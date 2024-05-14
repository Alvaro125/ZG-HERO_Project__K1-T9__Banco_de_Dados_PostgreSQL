import "./style.css"
import React, { useState } from "react"
import { Bounce, toast } from 'react-toastify';
import { INaturalPerson } from "../../interfaces/person"
import { Address } from "../../entity/Address"
import Skills from "../../components/skills"
import { useNaturalPeople } from "../../context/naturalPerson";
import { NaturalPerson } from "../../entity/NaturalPerson";

interface IProps {
    onBack: any
}

function CreateNaturalPerson({ onBack }: IProps) {
    const [skills, setSkills] = useState<string[]>([])
    const [skill, setSkill] = useState<string>("")
    const { addNaturalPerson } = useNaturalPeople() as any;

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
            if (!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$/.test(data.get('password') as string)) {
                throw "Senha inválido"
            }
            if (!/^[\w\d\s.,!?()-]{10,}$/.test(data.get('description') as string)) {
                throw "Descrição inválido"
            }
            // eslint-disable-next-line no-useless-escape
            if (!/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/.test(data.get('cpf') as string)) {
                throw "CPF inválido"
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
            if (!data.get('age') || !(data.get('age') as string)?.length) {
                throw "Idade inválido"
            }
            const naturalPerson: INaturalPerson = new NaturalPerson(
                data.get('name') as string,
                data.get('email') as string,
                data.get('password') as string,
                data.get('description') as string,
                new Address(
                    data.get('country') as string,
                    data.get('state') as string,
                    data.get('cep') as string,
                ),
                data.get('cpf') as string,
                parseInt(data.get('age') as string, 10),
                skills
            )
            fetch('http://localhost:8081/candidato', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(
                    {
                    "cpf": naturalPerson.cpf.replace(/[^\d]/g, ''),
                    "age": naturalPerson.age,
                    "name": naturalPerson.name,
                    "email": naturalPerson.email,
                    "description": naturalPerson.description,
                    "address": {
                        "country": naturalPerson.address.country,
                        "state": naturalPerson.address.state,
                        "cep": naturalPerson.address.cep.replace(/[^\d]/g, '')
                    },
                    "password": naturalPerson.password
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
            addNaturalPerson(naturalPerson);
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
            <label htmlFor="name">Nome do Candidato:</label>
            <input type="text" name="name" id="name" />

            <label htmlFor="email">Email do Candidato:</label>
            <input type="email" name="email" id="email" />

            <label htmlFor="password">Senha do Candidato:</label>
            <input type="password" name="password" id="password" />

            <label htmlFor="description">Descrição do Candidato:</label>
            <textarea name="description" id="description" cols={30} rows={10}></textarea>

            <label htmlFor="cpf">CPF do Candidato:</label>
            <input type="text" name="cpf" id="cpf" />

            <label htmlFor="age">Idade do Candidato:</label>
            <input type="number" name="age" id="age" />

            <fieldset>
                <legend>Endereço</legend>
                <label htmlFor="country">Pais:</label>
                <input type="text" name="country" id="country" />
                <label htmlFor="state">Estado:</label>
                <input type="text" name="state" id="state" />
                <label htmlFor="cep">CEP:</label>
                <input type="text" name="cep" id="cep" />
            </fieldset>

            <label htmlFor="skill">Skills do Candidato:</label>
            <div>
                <input type="text" name="skill" id="skill" onChange={(evt) => setSkill(evt.target.value)} value={skill} />
                <button onClick={handleAddSkills} type="button">+</button>
            </div>
            <Skills list={skills} onRemove={handleRemove}></Skills>

            <input type="submit" value="Criar" />
        </form>
    </>
}
export default CreateNaturalPerson