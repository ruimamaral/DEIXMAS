<template>
  <h2>Cabazes disponíveis</h2>
  <v-text-field
    v-model="search"
    append-inner-icon="fas fa-search"
    label="Pesquisar"
    single-line
    hide-details
  />
  <v-data-table
    :headers="headers"
    :items="baskets"
    :search="search"
    :loading="loading"
    item-key="id"
    no-data-text="Não há cabazes disponíveis para sorteio."
  >
    <template v-slot:item.type="{ item }">
      <v-chip
        v-if="item.raw.vegetarian === 'true'"
        color="green"
        text-color="white"
      >
        Vegetariano
      </v-chip>
      <v-chip v-else color="brown" text-color="white"> Normal </v-chip>
    </template>
    <!-- <template v-slot:item.actions="{ item }">
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
    </template> -->
  </v-data-table>
</template>

<script setup lang="ts">
import BasketDto from '@/models/BasketDto';
import RemoteServices from '@/services/RemoteServices';
import { reactive, ref } from 'vue';

let search = ref('');
let loading = ref(true);

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
    title: 'Preço',
    key: 'unitPrice',
    value: 'unitPrice',
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
  /* {
    title: 'Ações',
    key: 'actions',
    sortable: false,
    filterable: false,
  } */
];
const baskets: BasketDto[] = reactive([]);

const updateTable = async () => RemoteServices.getBaskets().then((data) => {
  loading.value = true;
  baskets.length = 0;
  baskets.push(...data);
  loading.value = false;
});

updateTable();
</script>
