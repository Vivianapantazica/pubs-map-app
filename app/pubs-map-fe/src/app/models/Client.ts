export class Client {
  private _clientId: number;
  private _email: string;

  constructor(clientId: number, email: string) {
    this._clientId = clientId;
    this._email = email;
  }

  get clientId(): number {
    return this._clientId;
  }

  set clientId(value: number) {
    this._clientId = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }
}
