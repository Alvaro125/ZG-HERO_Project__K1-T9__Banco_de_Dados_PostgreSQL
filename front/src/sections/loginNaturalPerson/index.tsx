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

function LoginNaturalPerson({ onBack }: IProps) {
    const { addNaturalPerson } = useNaturalPeople() as any;

    const handleSubmit = function (evt: React.FormEvent) {
        evt.preventDefault()
        try {
            const data = new FormData(evt.target as HTMLFormElement)
            if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(data.get('email') as string)) {
                throw "Email inválido"
            }
            if (!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$/.test(data.get('password') as string)) {
                throw "Senha inválido"
            }
            fetch('http://localhost:8081/login/candidato', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(
                    {
                    "email": data.get('email') as string,
                    "senha": data.get('password') as string
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

    return <>
        <form onSubmit={handleSubmit}>
            <label htmlFor="email">Email do Candidato:</label>
            <input type="email" name="email" id="email" />

            <label htmlFor="password">Senha do Candidato:</label>
            <input type="password" name="password" id="password" />

            <input type="submit" value="Criar" />
        </form>
    </>
}
export default LoginNaturalPerson