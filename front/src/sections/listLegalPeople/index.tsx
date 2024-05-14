import { useLegalPeaple } from "../../context/legalPerson"
import { ILegalPerson } from "../../interfaces/person";

function ListLegalPeople() {
    const { legalPeaple } = useLegalPeaple() as any;

    return (
        <>
            <ul>
                {legalPeaple.map((lp: ILegalPerson, index:number) => (
                    <li key={index}>
                        <strong>Nome:</strong> {lp.name} <br />
                        <strong>Email:</strong> {lp.email} <br />
                        <strong>Descrição:</strong> {lp.description} <br />
                        <strong>Skills:</strong> {lp.skills.length ? lp.skills.join(" | "): <i>não informado</i>} <br />
                        <strong>Endereço:</strong> {lp.address.country}, {lp.address.state}, CEP: {lp.address.cep} <br />
                        <strong>CNPJ:</strong> {lp.cnpj} <hr />
                    </li>
                ))}
            </ul>
        </>
    );
}
export default ListLegalPeople