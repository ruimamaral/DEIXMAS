<template>
  <v-container>
    <v-row>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>
      <v-col align-self="start">
        <h2>Sorteios de Cabazes</h2>
      </v-col>
      <v-col class="text-end">
        <v-btn
          flat
          color="blue-darken-1"
          size="large"
          @click="showCreate"
        >
          Criar Sorteio
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>
      <v-text-field
        v-model="search"
        append-inner-icon="fas fa-search"
        label="Pesquisar"
        single-line
        hide-details
      />
    </v-row>
  </v-container>
  <v-dialog v-model="editDialog" persistent width="800">
    <v-card>
      <v-card-title>
        <span class="text-h5"> {{ dialogTitle }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="editForm"> 
            <v-row justify="center">
              <v-col cols="28" sm="14" md="7">
                <v-textarea
                  label="Tema*"
                  v-model="editing.theme"
                  :rules="themeRules"
                  required
                ></v-textarea>
              </v-col>
              <v-col cols="12" sm="6" md="3">
                <v-text-field
                  label="Data*"
                  v-model="editing.date"
                  type="date"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="20" sm="10" md="5">
                <v-autocomplete
                  v-model="editing.normalOption"
                  :items="normalOptions"
                  outlined
                  color="blue-grey-lighten-2"
                  item-title="name"
                  item-value="id"
                  label="Opção de Cabaz Normal"
                  required
                ></v-autocomplete>
              </v-col>
              <v-col cols="20" sm="10" md="5">
                <v-autocomplete
                  v-model="editing.vegetarianOption"
                  :items="vegetarianOptions"
                  outlined
                  color="blue-grey-lighten-2"
                  item-title="name"
                  item-value="id"
                  label="Opção de Cabaz Vegetariano"
                  required
                ></v-autocomplete>
              </v-col>
            </v-row>
          </v-form>
        </v-container>
        <small>*Campo obrigatório</small>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          outline
          color="blue-darken-1"
          variant="text"
          :disabled="updatingRaffle"
          size="x-large"
          @click="editDialog = false"
        >
          Cancelar
        </v-btn>
        <v-btn
          flat
          color="blue-darken-1"
          :loading="updatingRaffle"
          :variant="updatingRaffle? 'tonal' : undefined"
          size="x-large"
          @click="confirmButton(editing)"
        >
          Confirmar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-dialog v-model="removeDialog" persistent width="800">
    <v-card>
      <v-card-title>
        <span class="text-h5">Cancelar sorteio</span>
      </v-card-title>
      <v-card-text>
        {{ `Tem a certeza que pretende remover o sorteio ${removing.id}?` }}
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="blue-darken-1"
          size="large"
          :disabled="removingRaffle"
          variant="text"
          @click="removeDialog = false"
        >
          Não
        </v-btn>
        <v-btn
          color="red-darken-1"
          :loading="removingRaffle"
          :variant="removingRaffle? 'tonal' : undefined"
          size="large"
          @click="removeRaffle(removing)"
        >
          Sim
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-data-table
    :headers="headers"
    :items="raffles"
    :search="search"
    :loading="loading"
    item-key="id"
    no-data-text="Sem sorteios a apresentar."
  >
    <template v-slot:item.actions="{ item }">
      <v-row no-gutters>
        <v-col cols="2">
          <v-btn
            class="square-button"
            color="blue"
            @click="showEdit(item.raw)"
            icon
          >
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="2">
          <v-btn
            class="square-button"
            color="red"
            @click="showRemove(item.raw)"
            icon
          >
            <v-icon>fas fa-trash</v-icon>
          </v-btn>
        </v-col>
      </v-row>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import BasketDto from '@/models/BasketDto';
import RaffleDto from '@/models/RaffleDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
let updatingRaffle = ref(false);
let removingRaffle = ref(false);
let loading = ref(true);
let editDialog = ref(false);
let removeDialog = ref(false);
let editing = ref(new RaffleDto());
let removing = ref(new RaffleDto());
let editForm = ref(null);
let themeRules = [
  (v : string) => !!v || 'Tema é um campo obrigatório',
  (v: string) => (v && v.length <= 100) || 'O tema deve conter 100 ou menos carateres!'
]
let confirmButton: (raffle: RaffleDto) => Promise<void>;
let dialogTitle: string;

const headers = [
  { 
    title: 'Tema',
    key: 'theme',
    value: 'theme',
    sortable: true,
    filterable: false
  },
  {
    title: 'Data',
    key: 'date',
    value: 'date',
    sortable: true,
    filterable: true,
  },
  {
    title: 'Ações',
    key: 'actions',
    sortable: false,
    filterable: false,
  }
];

const raffles: RaffleDto[] = reactive([]);
const vegetarianOptions: BasketDto[] = reactive([]);
const normalOptions: BasketDto[] = reactive([]);

const editRaffle = async (raffle: RaffleDto) => {
  const { valid } = await editForm.value.validate();
  if (valid) {
    updatingRaffle.value = true;
    await RemoteServices.updateRaffle(raffle.id, raffle);
    await updateTable();
    editDialog.value = false;
    updatingRaffle.value = false;
  }
}

const createRaffle = async (raffle: RaffleDto) => {
  const { valid } = await editForm.value.validate();
  if (valid) {
    updatingRaffle.value = true;
    await RemoteServices.createRaffle(raffle);
    await updateTable();
    editDialog.value = false;
    updatingRaffle.value = false;
  }
}

const removeRaffle = async (raffle: RaffleDto) => {
  removingRaffle.value = true;
  await RemoteServices.deleteRaffle(raffle.id);
  await updateTable();
  removeDialog.value = false;
  removingRaffle.value = false;
}

const showEdit = (raffle: RaffleDto) => {
  editing.value = {...raffle};
  editDialog.value = true;
  dialogTitle = `Editar Sorteio ${editing.value.id}`;
  confirmButton = editRaffle;
}

const showCreate = (raffle: RaffleDto) => {
  editing.value = {...new RaffleDto()};
  editDialog.value = true;
  dialogTitle = `Criar Sorteio`;
  confirmButton = createRaffle;
}

const showRemove = (raffle: RaffleDto) => {
  removing.value = {...raffle};
  removeDialog.value = true;
}

const updateTable = async () => RemoteServices.getRaffles().then((data) => {
  raffles.length = 0;
  raffles.push(...data);
  loading.value = false;
});

const fetchBasketOptions = async () => RemoteServices.getBaskets().then((baskets) => {
  vegetarianOptions.length = 0;
  normalOptions.length = 0;
  normalOptions.push(...baskets.filter(basket => !basket.vegetarian));
  vegetarianOptions.push(...baskets.filter(basket => basket.vegetarian));
});

fetchBasketOptions();
updateTable();
</script>
