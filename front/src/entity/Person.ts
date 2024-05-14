import { IPerson } from "../interfaces/person";
import { Address } from "./Address";

export class Person implements IPerson {
  constructor(
    public name: string,
    public email: string,
    public description: string,
    public address: Address,
    public skills: string[]
  ) {}
}
