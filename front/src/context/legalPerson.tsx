import React, { createContext, useContext, useState } from "react";
import { ILegalPerson } from "../interfaces/person";
import { LegalPerson } from "../entity/LegalPerson";
import { Address } from "../entity/Address";

// Definindo o contexto
const LegalPeapleContext = createContext([]);

// Provedor do contexto
export const LegalPeopleProvider = ({ children }:any) => {
  const [legalPeaple, setLegalPeaple] = useState<LegalPerson[]>([
    new LegalPerson(
      "Empresa Xuxa",
      "empresax@email.com",
      "Empresa de software",
      new Address("Brasil", "São Paulo", "111111-111"),
      "85.806.953/0001-02"
    ),
    new LegalPerson(
      "Empresa Yuri",
      "empresay@email.com",
      "Empresa de marketing digital",
      new Address("Brasil", "Rio de Janeiro", "222222-222"),
      "69.684.604/0001-09"
    ),
    new LegalPerson(
      "Empresa Zeta",
      "empresaz@email.com",
      "Empresa de contabilidade",
      new Address("Brasil", "Minas Gerais", "333333-333"),
      "98.440.771/0001-02"
    ),
    new LegalPerson(
      "Empresa Walter",
      "empresaw@email.com",
      "Empresa de logística",
      new Address("Portugal", "Lisboa", "444444-444"),
      "82.728.538/0001-81"
    ),
    new LegalPerson(
      "Empresa Valter",
      "empresav@email.com",
      "Empresa de turismo",
      new Address("Argentina", "Buenos Aires", "555555-555"),
      "37.028.993/0001-10"
    ),
  ]);

  const addLegalPerson = (legalPerson: ILegalPerson) => {
    setLegalPeaple([...legalPeaple, legalPerson]);
  };

  const value:any = {
    legalPeaple,addLegalPerson
  }

  return (
    <LegalPeapleContext.Provider value={value}>
      {children}
    </LegalPeapleContext.Provider>
  );
};

// Hook personalizado para acessar o contexto
export const useLegalPeaple = () => useContext(LegalPeapleContext);
