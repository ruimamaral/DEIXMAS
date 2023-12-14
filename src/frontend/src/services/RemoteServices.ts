import axios from 'axios';
import type { AxiosResponse } from 'axios';
import { useAppearanceStore } from '@/stores/appearance';
import DeixmasError from '@/models/DeixmasError';
import type ParticipantDto from '@/models/ParticipantDto';
import type BasketDto from '@/models/BasketDto';
import RaffleDto from '@/models/RaffleDto';

const httpClient = axios.create();
httpClient.defaults.timeout = 50000;
httpClient.defaults.baseURL = import.meta.env.VITE_ROOT_API;
httpClient.defaults.headers.post['Content-Type'] = 'application/json';

export default class RemoteServices {
  static async getParticipants(): Promise<ParticipantDto[]> {
    return httpClient.get('/participants');
  }

  static async createParticipants(
    participant: ParticipantDto
  ): Promise<ParticipantDto> {
    return httpClient.post('/participants', participant);
  }

  static async updateParticipant(
    id: number, participant: ParticipantDto
  ): Promise<ParticipantDto> {
    return httpClient.put(`/participants/${id}`, participant);
  }

  static async deleteParticipant(
    id: number
  ): Promise<ParticipantDto> {
    return httpClient.delete(`/participants/${id}`);
  }

  static async getBaskets(): Promise<BasketDto[]> {
    return httpClient.get('/baskets');
  }

  static async getRaffles(): Promise<RaffleDto[]> {
    return httpClient.get('/raffles');
  }
  
  static async getRaffle(
    id: number
  ): Promise<RaffleDto> {
    return httpClient.get(`/raffles/${id}`);
  }

  static async createRaffle(
    raffle: RaffleDto
  ): Promise<RaffleDto> {
    console.log(raffle);
    return httpClient.post('/raffles', raffle);
  }

  static async updateRaffle(
    id: number, raffle: RaffleDto
  ): Promise<RaffleDto> {
    return httpClient.put(`/raffles/${id}`, raffle);
  }

  static async deleteRaffle(
    id: number
  ): Promise<RaffleDto> {
    return httpClient.delete(`/raffles/${id}`);
  }

  static async participate(
    id: number, raffle_id: number
  ): Promise<void> {
    return httpClient.post(`/participants/${id}/raffles/${raffle_id}`);
  }

  static async leaveRaffle(
    id: number, raffle_id: number
  ): Promise<void> {
    return httpClient.delete(`/participants/${id}/raffles/${raffle_id}`);
  }

  static async placeOrder(
    id: number
  ): Promise<void> {
    return httpClient.post(`/orders/${id}/}`);
  }

  static async cancelOrder(
    id: number
  ): Promise<void> {
    return httpClient.delete(`/orders/${id}/}`);
  }

  static async getParticipating(
    id: number
  ): Promise<RaffleDto[]> {
    return httpClient.get(`/participants/${id}/raffles`);
  }

  static async getRaffleParticipants(
    id: number
  ): Promise<RaffleDto[]> {
    return httpClient.get(`/raffles/${id}/participants`);
  }


  static async errorMessage(error: any): Promise<string> {
    if (error.message === 'Network Error') {
      return 'Unable to connect to the server';
    } else if (error.message.split(' ')[0] === 'timeout') {
      return 'Request timeout - Server took too long to respond';
    } else {
      return error.response?.data?.message ?? 'Unknown Error';
    }
  }

  static async handleError(error: any): Promise<never> {
    const deixmasErr = new DeixmasError(
      await RemoteServices.errorMessage(error),
      error.response?.data?.code ?? -1
    );
    const appearance = useAppearanceStore();
    appearance.pushError(deixmasErr);
    appearance.loading = false;
    throw deixmasErr;
  }
}

httpClient.interceptors.request.use(
  (request) => request,
  RemoteServices.handleError
);
httpClient.interceptors.response.use(
  (response) => response.data,
  RemoteServices.handleError
);
