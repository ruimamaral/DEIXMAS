export default class RaffleDto {
  id?: number;
  theme?: string;
  date?: string;
  normalOption?: number;
  vegetarianOption?: number;
  orderPlaced?: boolean;

  constructor(obj?: Partial<RaffleDto>) {
    Object.assign(this, obj);
  }
}
