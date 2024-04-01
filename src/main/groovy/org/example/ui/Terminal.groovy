package org.example.ui

import org.example.entity.AddressEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.LegalPersonEntity
import org.example.services.LegalPersonService
import org.example.services.NaturalPersonService
import org.example.services.SkillService

class Terminal {
    static NaturalPersonService naturalPersonService
    static LegalPersonUI legalPersonUI
    static NaturalPersonUI naturalPersonUI
    static SkillsUI skillsUI
    static JobsUI jobsUI

    Terminal(){
        naturalPersonUI = new NaturalPersonUI()
        legalPersonUI = new LegalPersonUI()
        skillsUI = new SkillsUI()
        jobsUI = new JobsUI()
    }

    void run() {
        def option = ""
        while (!option.equals("0")){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
            println """
        #####################################
        #PAINEL                             #
        #####################################
        # 0-Sair                            #
        # 1-Listar Empresas                 #
        # 2-Adicionar Empresa               #
        # 3-Atualizar Empresa               #
        # 4-Deletar Empresa                 #
        # 5-Listar Candidatos               #
        # 6-Adicionar Candidato             #
        # 7-Atualizar Candidato             #
        # 8-Deletar Candidato               #
        # 9-Listar Competencias             #
        #10-Adicionar Competencia           #
        #11-Atualizar Competencia           #
        #12-Deletar Competencia             #
        #13-Listar Empregos                 #
        #14-Adicionar Emprego               #
        #15-Atualizar Emprego               #
        #16-Deletar Emprego                 #
        #####################################
        """
            print 'Opção:'
            option = br.readLine()
            switch (option) {
                case "0":
                    println """
                ░▄▀█░█▀▄░█▀▀░█░█░█▀
                ░█▀█░█▄▀░██▄░█▄█░▄█
                ░░░░░░░░░░░░░░░░░░░░
                """
                    return
                case "1":
                    legalPersonUI.read()
                    break
                case "2":
                    legalPersonUI.create(br)
                    break
                case "3":
                    legalPersonUI.update(br)
                    break
                case "4":
                    legalPersonUI.delete(br)
                    break
                case "5":
                    naturalPersonUI.read()
                    break
                case "6":
                    naturalPersonUI.create(br)
                    break
                case "7":
                    naturalPersonUI.update(br)
                    break
                case "8":
                    naturalPersonUI.delete(br)
                    break
                case "9":
                    skillsUI.read()
                    break
                case "10":
                    skillsUI.create(br)
                    break
                case "11":
                    skillsUI.update(br)
                    break
                case "12":
                    skillsUI.delete(br)
                    break
                case "13":
                    jobsUI.read()
                    break
                case "14":
                    jobsUI.create(br)
                    break
                case "15":
                    jobsUI.update(br)
                    break
                case "16":
                    jobsUI.delete(br)
                    break
                default:
                    println """
                ░█▀▀░█▀█░█▀█░█▀█░█▀█
                ░██▄░█▀▄░█▀▄░█▄█░█▀▄
                ░░░░░░░░░░░░░░░░░░░░
                """
            }
        }
    }
}