import { ILegalPerson } from "../interfaces/person";
import { Address } from "./Address";
import { Person } from "./Person";

export class LegalPerson extends Person implements ILegalPerson{
  constructor(
    public name: string,
    public email: string,
    public password: string,
    public description: string,
    public address: Address,
    public cnpj: string,
    public skills: string[]=[]
  ) {
    super(name,email,description,address,skills)
  }
}
