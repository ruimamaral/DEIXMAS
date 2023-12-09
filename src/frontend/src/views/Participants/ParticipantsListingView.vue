<template>
  <h2>Participantes Registados</h2>
  <v-text-field
    v-model="search"
    append-inner-icon="fas fa-search"
    label="Pesquisar"
    single-line
    hide-details
  />
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
      <v-container>
        <v-row>
          <v-col cols="4">

            <v-dialog v-model="editDialog" persistent width="">
              <template v-slot:activator="{ props }">
                <!-- <v-btn color="primary" v-bind="props"> Open Dialog </v-btn> -->
                  <!--@click="editing = {...item.raw}"-->
                  <!-- @click="updateAttendee(item.raw.id, item.raw)" -->
                <v-btn
                  v-bind="props"
                  class="square-button"
                  color="blue"
                  @click="editing = {...item.raw}"
                  icon
                >
                  <v-icon>fas fa-pencil</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5"> {{ `Editar participante ${editing.id}` }}</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-form ref="editForm"> 
                      <v-row>
                        <v-col cols="14" sm="8" md="4">
                          <v-text-field
                            label="Nome*"
                            v-model="editing.name"
                            :rules="nameRules"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-col cols="6" sm="4" md="3">
                          <v-text-field
                            label="Ist ID*"
                            hint="Formato: ist1xxxxxx"
                            v-model="editing.istId"
                            :rules="istIdRules"
                            required
                          ></v-text-field>
                        </v-col>

                      </v-row>

                    </v-form>
                  </v-container>
                  <small>*Campo obrigatório</small>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="blue-darken-1"
                    class="mt-4"
                    variant="text"
                    @click="editDialog = false"
                  >
                    Close
                  </v-btn>
                  <v-btn
                    color="blue-darken-1"
                    :loading="updatingParticipant"
                    :variant="updatingParticipant? 'tonal' : undefined"
                    class="mt-4"
                    variant="text"
                    @click="editParticipant(editing)"
                  >
                    Save
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-btn @click="console.log('delete')"
              class="square-button"
              color="red"
              icon
            >
              <v-icon>fas fa-trash</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
import type ParticipantDto from '@/models/ParticipantDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
let updatingParticipant = ref(false);
let name = ref('');
let loading = ref(true);
let editDialog = ref(false);
let editing: ParticipantDto = ref({});
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

const participants: ParticipantDto[] = reactive([]);

const editParticipant = async (editing: ParticipantDto) => {
  const { valid } = await editForm.value.validate();
  if (valid) {
    updatingParticipant.value = true;
    await RemoteServices.updateParticipant(editing.id, editing);
    await updateTable();
    editDialog.value = false;
    updatingParticipant.value = false;
  }
}

const updateTable = async () => RemoteServices.getParticipants().then((data) => {
  participants.length = 0;
  participants.push(...data);
  loading.value = false;
});

updateTable();
</script>
