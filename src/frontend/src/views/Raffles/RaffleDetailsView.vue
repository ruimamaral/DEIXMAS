<template>
  <v-container>
    <v-row>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>
      <v-col align-self="start">
        <h2> {{ `Sorteio ${raffleId}` }} </h2>
      </v-col>
      <v-col class="text-end">
        <v-btn
          flat
          color="blue-darken-1"
          size="large"
          @click="showEdit"
        >
          Editar Sorteio
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>

    </v-row>
    <v-row>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>
      <h2> {{ `Participantes inscritos` }} </h2>
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
        <span class="text-h5"> {{ `Editar Sorteio ${raffle.id}` }}</span>
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
          @click="editRaffle()"
        >
          Guardar
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
          @click="removeRaffle()"
        >
          Sim
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-data-table
    :headers="headers"
    :items="participants"
    :search="search"
    :loading="loading"
    item-key="id"
    no-data-text="Este sorteio não tem participantes inscritos."
  >
    <template v-slot:item.type="{ item }">
      <v-chip
        v-if="item.raw.type === 'TEACHER'"
        color="purple"
        text-color="white"
      >
        Professor
      </v-chip>
      <v-chip v-else color="green" text-color="white"> Bolseiro </v-chip>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-row no-gutters>
        <v-col cols="2">
          <v-btn
            class="square-button"
            color="red"
            @click="showRemoveParticipant(item.raw)"
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
import { useRoute } from 'vue-router';

const router = useRoute();
let raffleId = ref(router.params.id);
let raffle = ref(new RaffleDto())
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
const headers = [
  { 
    title: 'ID',
    key: 'id',
    value: 'id',
    sortable: true,
    filterable: false
  },
  {
    title: 'Nome',
    key: 'name',
    value: 'name',
    sortable: true,
    filterable: true,
  },
  {
    title: 'IST ID',
    key: 'istId',
    value: 'istId',
    sortable: true,
    filterable: true,
  },
  {
    title: 'Tipo',
    key: 'type',
    value: 'type',
    sortable: true,
    filterable: false,
  },
  {
    title: 'Ações',
    key: 'actions',
    sortable: false,
    filterable: false,
  }
];

const participants: RaffleDto[] = reactive([]);
const vegetarianOptions: BasketDto[] = reactive([]);
const normalOptions: BasketDto[] = reactive([]);

const editRaffle = async () => {
  const { valid } = await editForm.value.validate();
  if (valid) {
    updatingRaffle.value = true;
    await RemoteServices.updateRaffle(raffle.value.id, editing.value);
    await updateRaffle();
    editDialog.value = false;
    updatingRaffle.value = false;
  }
}

const removeRaffle = async () => {
  removingRaffle.value = true;
  await RemoteServices.deleteRaffle(raffle.value.id);
  removeDialog.value = false;
  removingRaffle.value = false;
  router.push('/raffles')
}

const showEdit = () => {
  editing.value = {...raffle.value};
  editDialog.value = true;
}

const showRemove = (raffle: RaffleDto) => {
  removing.value = {...raffle};
  removeDialog.value = true;
}

const updateRaffle = async () => RemoteServices.getRaffle(raffleId.value).then((raf) => {
  raffle.value = {...raf};
  loading.value = false;
});

const fetchParticipants = async () => RemoteServices.getRaffleParticipants(raffleId.value).then((raffleParticipants) => {
  participants.push(...raffleParticipants);
});

const fetchBasketOptions = async () => RemoteServices.getBaskets().then((baskets) => {
  vegetarianOptions.length = 0;
  normalOptions.length = 0;
  normalOptions.push(...baskets.filter(basket => !basket.vegetarian));
  vegetarianOptions.push(...baskets.filter(basket => basket.vegetarian));
});

fetchBasketOptions();
updateRaffle();
fetchParticipants();
</script>
