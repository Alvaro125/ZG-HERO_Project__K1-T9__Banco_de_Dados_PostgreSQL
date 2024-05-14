import { IAddress } from "./address"

export interface IPerson{
    name: string
    email: string
    description: string
    address: IAddress
    skills: string[]
    password: string
}
export interface ILegalPerson extends IPerson{
    cnpj: string
}
export interface INaturalPerson extends IPerson{
    cpf: string
    age: number
}