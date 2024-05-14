import { INaturalPerson } from "../interfaces/person";
import { Address } from "./Address";
import { Person } from "./Person";

export class NaturalPerson extends Person implements INaturalPerson {
  constructor(
    public name: string,
    public email: string,
    public password: string,
    public description: string,
    public address: Address,
    public cpf: string,
    public age: number,
    public skills: string[] = []
  ) {
    super(name, email, description, address, skills);
  }
}
