import "./style.css"
import React, { useState } from "react"
import { Bounce, toast } from 'react-toastify';
import { ILegalPerson } from "../../interfaces/person"
import { LegalPerson } from "../../entity/LegalPerson"
import { Address } from "../../entity/Address"
import Skills from "../../components/skills"
import { useLegalPeaple } from "../../context/legalPerson";

interface IProps{
    onBack: any
}

function CreateLegalPerson({onBack}:IProps) {
    const [skills, setSkills] = useState<string[]>([])
    const [skill, setSkill] = useState<string>("")
    const { addLegalPerson } = useLegalPeaple() as any;

    const handleSubmit = function (evt: React.FormEvent) {
        evt.preventDefault()
        try {
            const data = new FormData(evt.target as HTMLFormElement)
            if (!/^[a-zA-ZÀ-ÿ]{3,}(?: [a-zA-ZÀ-ÿ]+)+$/.test(data.get('name') as string)) {
                throw "Nome inválido"
            }
            if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(data.get('email') as string)) {
                throw "Email inválido"
            }
            if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/.test(data.get('password') as string)) {
                throw "Senha inválido"
            }
            if (!/^[\w\d\s.,!?()-]{10,}$/.test(data.get('description') as string)) {
                throw "Descrição inválido"
            }
            // eslint-disable-next-line no-useless-escape
            if (!/^\d{2}\.\d{3}\.\d{3}\/\d{4}\-\d{2}$/.test(data.get('cnpj') as string)) {
                throw "CNPJ inválido"
            }
            if (!/^.{3,}$/.test(data.get('country') as string)) {
                throw "Pais inválido"
            }
            if (!/^.{3,}$/.test(data.get('state') as string)) {
                throw "Estado inválido"
            }
            if (!/^\d{5}-\d{3}$/.test(data.get('cep') as string)) {
                throw "CEP inválido"
            }
            const legalPerson: ILegalPerson = new LegalPerson(
                data.get('name') as string,
                data.get('email') as string,
                data.get('password') as string,
                data.get('description') as string,
                new Address(
                    data.get('country') as string,
                    data.get('state') as string,
                    data.get('cep') as string,
                ),
                data.get('cnpj') as string,
                skills
            )
            fetch('http://localhost:8081/candidato', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(
                    {
                    "cnpj": legalPerson.cnpj.replace(/[^\d]/g, ''),
                    "name": legalPerson.name,
                    "email": legalPerson.email,
                    "description": legalPerson.description,
                    "address": {
                        "country": legalPerson.address.country,
                        "state": legalPerson.address.state,
                        "cep": legalPerson.address.cep.replace(/[^\d]/g, '')
                    },
                    "password": legalPerson.password
                    }
                ),
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro na requisição');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Dados recebidos:', data);
                })
                .catch(error => {
                    console.error('Erro:', error);
                });
            addLegalPerson(legalPerson);
            onBack()
        } catch (error) {
            toast.error(error as string, {
                position: "bottom-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                transition: Bounce,
            });
        }
    }

    const handleAddSkills = function () {
        const regex = /^.{2,}$/;
        if (regex.test(skill)) {
            setSkills((prev) => [...prev, skill])
        } else {
            toast.error("Skill deve ter mais de 2 caractes" as string, {
                position: "bottom-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
                transition: Bounce,
            });
        }
    }

    function handleRemove(key: number) {
        const newList = skills.filter((item, i) => i !== key);
        setSkills(newList);
    }

    return <>
        <form onSubmit={handleSubmit}>
            <label htmlFor="name">Nome da Empresa:</label>
            <input type="text" name="name" id="name" />

            <label htmlFor="email">Email da Empresa:</label>
            <input type="email" name="email" id="email" />

            <label htmlFor="password">Senha do Candidato:</label>
            <input type="password" name="password" id="password" />

            <label htmlFor="description">Descrição da Empresa:</label>
            <textarea name="description" id="description" cols={30} rows={10}></textarea>

            <label htmlFor="cnpj">CNPJ da Empresa:</label>
            <input type="text" name="cnpj" id="cnpj" />

            <fieldset>
                <legend>Endereço</legend>
                <label htmlFor="country">Pais:</label>
                <input type="text" name="country" id="country" />
                <label htmlFor="state">Estado:</label>
                <input type="text" name="state" id="state" />
                <label htmlFor="cep">CEP:</label>
                <input type="text" name="cep" id="cep" />
            </fieldset>

            <label htmlFor="skill">Skills da Empresa:</label>
            <div>
                <input type="text" name="skill" id="skill" onChange={(evt) => setSkill(evt.target.value)} value={skill} />
                <button onClick={handleAddSkills} type="button">+</button>
            </div>
            <Skills list={skills} onRemove={handleRemove}></Skills>

            <input type="submit" value="Criar" />
        </form>
    </>
}
export default CreateLegalPerson