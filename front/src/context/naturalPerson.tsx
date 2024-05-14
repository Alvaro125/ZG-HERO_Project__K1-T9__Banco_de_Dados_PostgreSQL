import { createContext, useContext, useState } from "react";
import { INaturalPerson } from "../interfaces/person";
import { Address } from "../entity/Address";
import { NaturalPerson } from "../entity/NaturalPerson";

// Definindo o contexto
const NaturalPeopleContext = createContext([]);

// Provedor do contexto
export const NaturalPeopleProvider = ({ children }: any) => {
  const [naturalPeople, setNaturalPeople] = useState<NaturalPerson[]>([
    new NaturalPerson(
      "João Silva", "joaosilva@email.com", "Desenvolvedor Python",
      new Address("Brasil", "São Paulo", "111111-111"),
      "675.257.460-01", 25, ["Python", "BackEnd"]
    ),
    new NaturalPerson(
      "Pedro Souza", "pedrosouza@email.com", "Analista de Dados",
      new Address("Brasil", "Minas Gerais", "333333-333"),
      "059.208.020-06", 28
    ),
    new NaturalPerson(
      "Maria Oliveira", "mariaoliveira@email.com", "Engenheira de Software",
      new Address("Brasil", "Rio de Janeiro", "222222-222"),
      "342.032.040-03", 30
    ),
    new NaturalPerson(
      "Ana Costa", "anacosta@email.com", "Gerente de Projetos",
      new Address("Portugal", "Lisboa", "444444-444"),
      "635.824.990-81", 40
    ),
    new NaturalPerson(
      "Carlos Santos", "carloshs@email.com", "Designer UX/UI",
      new Address("Argentina", "Buenos Aires", "555555-555"),
      "601.903.320-18", 23
    )
  ]);

  const addNaturalPerson = (naturalPerson: INaturalPerson) => {
    setNaturalPeople([...naturalPeople, naturalPerson]);
  };

  const value: any = {
    naturalPeople, addNaturalPerson
  }

  return (
    <NaturalPeopleContext.Provider value={value}>
      {children}
    </NaturalPeopleContext.Provider>
  );
};

// Hook personalizado para acessar o contexto
export const useNaturalPeople = () => useContext(NaturalPeopleContext);
