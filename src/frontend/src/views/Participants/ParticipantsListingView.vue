<template>
  <h2>Participantes Registados</h2>
  <v-text-field
    v-model="search"
    append-inner-icon="fas fa-search"
    label="Pesquisar"
    single-line
    hide-details
  />
  <v-dialog v-model="editDialog" persistent width="800">
    <v-card>
      <v-card-title>
        <span class="text-h5"> {{ `Editar participante ${editing.id}` }}</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="editForm"> 
            <v-row justify="center">
              <v-col cols="28" sm="14" md="7">
                <v-text-field
                  label="Nome*"
                  v-model="editing.name"
                  :rules="nameRules"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="3">
                <v-text-field
                  label="Ist ID*"
                  hint="Formato: ist1xxxxxx"
                  v-model="editing.istId"
                  :rules="istIdRules"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col cols="20" sm="10" md="5">
                <v-select
                  v-model="editing.type"
                  :items="types"
                  :rules="[(v : string) => !!v || 'Tipo é um campo obrigatório!']"
                  item-value="val"
                  item-title="text"
                  label="Tipo"
                  chips
                  required
                ></v-select>
              </v-col>
              <v-col cols="20" sm="10" md="5">
                <v-select
                  v-model="editing.diet"
                  :items="diets"
                  :rules="[(v : string) => !!v || 'Dieta é um campo obrigatório!']"
                  item-value="val"
                  item-title="text"
                  label="Dieta"
                  chips
                  required
                ></v-select>
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
          :disabled="updatingParticipant"
          size="x-large"
          @click="editDialog = false"
        >
          Cancelar
        </v-btn>
        <v-btn
          flat
          color="blue-darken-1"
          :loading="updatingParticipant"
          :variant="updatingParticipant? 'tonal' : undefined"
          size="x-large"
          @click="editParticipant(editing)"
        >
          Guardar
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-dialog v-model="removeDialog" persistent width="800">
    <v-card>
      <v-card-title>
        <span class="text-h5"> Remover participante </span>
      </v-card-title>
      <v-card-text>
        {{ `Tem a certeza que pretende remover o participante ${removing.name} do sistema?` }}
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="blue-darken-1"
          size="large"
          :disabled="removingParticipant"
          variant="text"
          @click="removeDialog = false"
        >
          Não
        </v-btn>
        <v-btn
          color="red-darken-1"
          :loading="removingParticipant"
          :variant="removingParticipant? 'tonal' : undefined"
          size="large"
          @click="removeParticipant(removing)"
        >
          Remover
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
    no-data-text="Sem participantes a apresentar."
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
import ParticipantDto from '@/models/ParticipantDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
let updatingParticipant = ref(false);
let removingParticipant = ref(false);
let loading = ref(true);
let editDialog = ref(false);
let removeDialog = ref(false);
let editing = ref(new ParticipantDto());
let removing = ref(new ParticipantDto());
let editForm = ref(null);
let istIdRules = [
  (v: string) => !!v || 'Ist ID é um campo obrigatório!',
  (v: string) => /^\ist1[0-9]+$/.test(v) || 'O Ist ID deve respeitar o formato indicado!',
  (v: string) => (v && v.length <= 10) || 'O Ist ID deve conter 10 ou menos carateres!'
]
let nameRules = [
  (v : string) => !!v || 'Nome é um campo obrigatório',
  (v: string) => (v && v.length <= 50) || 'O nome deve conter 50 ou menos carateres!'
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

const types = [
  { text: 'Bolseiro', val: 'GRANTEE' },
  { text: 'Professor', val: 'TEACHER' },
];

const diets = [
  { text: 'Normal', val: 'REGULAR' },
  { text: 'Vegetariana', val: 'VEGETARIAN' },
];

const participants: ParticipantDto[] = reactive([]);

const showEdit = (participant: ParticipantDto) => {
  editing.value = {...participant};
  editDialog.value = true;
}

const showRemove= (participant: ParticipantDto) => {
  removing.value = {...participant};
  removeDialog.value = true;
}

const editParticipant = async (participant: ParticipantDto) => {
  const { valid } = await editForm.value.validate();
  if (valid) {
    updatingParticipant.value = true;
    await RemoteServices.updateParticipant(participant.id, participant);
    await updateTable();
    editDialog.value = false;
    updatingParticipant.value = false;
  }
}

const removeParticipant = async (participant: ParticipantDto) => {
  removingParticipant.value = true;
  await RemoteServices.deleteParticipant(participant.id);
  await updateTable();
  removeDialog.value = false;
  removingParticipant.value = false;
}

const updateTable = async () => RemoteServices.getParticipants().then((data) => {
  participants.length = 0;
  participants.push(...data);
  loading.value = false;
});

updateTable();
</script>
