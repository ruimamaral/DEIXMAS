export default class BasketDto {
  id?: number;
  name?: string;
  description?: string;
  unitPrice?: number;
  unitWeight?: number;
  vegetarian?: boolean;

  constructor(obj?: Partial<BasketDto>) {
    Object.assign(this, obj);
  }
}
