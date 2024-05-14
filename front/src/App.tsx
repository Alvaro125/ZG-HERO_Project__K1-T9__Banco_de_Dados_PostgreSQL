import { ToastContainer } from 'react-toastify';
import './App.css'
import 'react-toastify/dist/ReactToastify.css';
import CreateLegalPerson from './sections/createLegalPerson'
import { useState } from 'react';
import { LegalPeopleProvider } from './context/legalPerson';
import ListLegalPeople from './sections/listLegalPeople';
import { NaturalPeopleProvider } from './context/naturalPerson';
import CreateNaturalPerson from './sections/createNaturalPerson';
import ListNaturalPeople from './sections/listNaturalPeople';
import LoginNaturalPerson from './sections/loginNaturalPerson';

function App() {
  const [option, setOption] = useState<number>(0)
  function onBack() {
    setOption(0)
  }
  return (
    <>
      {option == 0 ?
        <div className='ctn'>
          <button onClick={() => setOption(1)}>Cadastrar empresa</button>
          <button onClick={() => setOption(2)}>Listar Empresas</button>
          <button onClick={() => setOption(6)}>Login Empresa</button>
          <button onClick={() => setOption(3)}>Cadastrar candidato</button>
          <button onClick={() => setOption(4)}>Listar candidatos</button>
          <button onClick={() => setOption(5)}>Login candidato</button>
        </div> :
        <button onClick={onBack}>Voltar</button>
      }
      <LegalPeopleProvider>
        {option == 1 && <CreateLegalPerson onBack={onBack} />}
        {option == 2 && <ListLegalPeople />}
      </LegalPeopleProvider>
      <NaturalPeopleProvider>
        {option == 3 && <CreateNaturalPerson onBack={onBack} />}
        {option == 4 && <ListNaturalPeople />}
        {option == 5 && <LoginNaturalPerson />}
      </NaturalPeopleProvider>
      <ToastContainer />
    </>
  )
}

export default App
