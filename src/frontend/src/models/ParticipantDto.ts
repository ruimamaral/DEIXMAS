export default class ParticipantDto {
  id?: number;
  name?: string;
  istId?: string;
  type?: string;
  diet?: string;

  constructor(obj?: Partial<ParticipantDto>) {
    Object.assign(this, obj);
  }
}
