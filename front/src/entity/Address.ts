import { IAddress } from "../interfaces/address";

export class Address implements IAddress {
    constructor(public country: string,
        public state: string,
        public cep: string,
    ) { }
}