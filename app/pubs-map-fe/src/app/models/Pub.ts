export class Pub {
  private _name: string;
  private _address: string;
  private _description: string;

  constructor(name: string, address: string, description: string) {
    this._name = name;
    this._address = address;
    this._description = description;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get address(): string {
    return this._address;
  }

  set address(value: string) {
    this._address = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }
}
