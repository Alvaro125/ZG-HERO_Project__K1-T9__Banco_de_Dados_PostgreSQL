import { useNaturalPeople } from "../../context/naturalPerson";
import { INaturalPerson } from "../../interfaces/person";

function ListNaturalPeople() {
    const { naturalPeople } = useNaturalPeople() as any;

    return (
        <>
            <ul>
                {naturalPeople.map((np: INaturalPerson, index: number) => (
                    <li key={index}>
                        <strong>Nome:</strong> {np.name} <br />
                        <strong>Email:</strong> {np.email} <br />
                        <strong>Descrição:</strong> {np.description} <br />
                        <strong>Skills:</strong> {np.skills.length ? np.skills.join(" | "): <i>não informado</i>} <br />
                        <strong>Endereço:</strong> {np.address.country}, {np.address.state}, CEP: {np.address.cep} <br />
                        <strong>CPF:</strong> {np.cpf} <br />
                        <strong>Idade:</strong> {np.age} <hr />
                    </li>
                ))}
            </ul>
        </>
    );
}
export default ListNaturalPeople