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
  </v-data-table>
</template>

<script setup lang="ts">
import type ParticipantDto from '@/models/ParticipantDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
let loading = ref(true);
const headers = [
  { title: 'ID', key: 'id', value: 'id', sortable: true, filterable: false },
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
  // TODO: maybe add another column with possible actions? (edit / delete)
];

const participants: ParticipantDto[] = reactive([]);

RemoteServices.getParticipants().then((data) => {
  participants.push(...data);
  loading.value = false;
});
</script>
