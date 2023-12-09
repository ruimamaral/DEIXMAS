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

            <v-dialog v-model="dialog" persistent width="1024">
              <template v-slot:activator="{ props }">
                <!-- <v-btn color="primary" v-bind="props"> Open Dialog </v-btn> -->
                  <!-- @click="updateAttendee(item.raw.id, item.raw)" -->
                <v-btn
                  class="square-button"
                  color="blue"
                  icon
                >
                  <v-icon>fas fa-pencil</v-icon>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">User Profile</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field label="Legal first name*" required></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          label="Legal middle name"
                          hint="example of helper text only on focus"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="6" md="4">
                        <v-text-field
                          label="Legal last name*"
                          v-model:model-value="name"
                          hint="example of persistent helper text"
                          persistent-hint
                          required
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-dialog>

            <v-btn @click="confirmDelete(item)"
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
let loading = ref(true);
let dialog = false;
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

RemoteServices.getParticipants().then((data) => {
  participants.push(...data);
  loading.value = false;
});
</script>
