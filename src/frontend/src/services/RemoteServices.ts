import axios from 'axios';
import type { AxiosResponse } from 'axios';
import { useAppearanceStore } from '@/stores/appearance';
import DeixmasError from '@/models/DeixmasError';
import type ParticipantDto from '@/models/ParticipantDto';

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
    return httpClient.delete(`/participants/${id}`, id);
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
