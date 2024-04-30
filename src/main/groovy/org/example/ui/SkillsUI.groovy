package org.example.ui

import org.example.config.Database
import org.example.entity.SkillEntity
import org.example.services.SkillService

class SkillsUI {
    private static SkillService skillService = new SkillService(Database.conn)

    void read() {
        println """
@@Lista de Competencias@@@@@@@@@@@@@@@@@@@@@@@@@@@@
${skillService.listSkills().join("\n")}
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        SkillEntity skill = new SkillEntity()
        print "Titulo da Competencia:"
        skill.title = readInput(br, "Titulo da Competencia")
        print "Descrição da Competencia:"
        skill.description = readInput(br, "Descrição da Competencia")
        skillService.addSkill(skill)
    }

    void update(BufferedReader br) {
        this.read()
        print "Informe o id da Skill:"
        Integer id = readIntegerInput(br, "id da Skill")
        SkillEntity skill = skillService.oneById(id)
        if(!skill){
            println "$id NÃO EXISTE"
        }else{
            skill = updateSkillDetails(br, skill)
            skillService.updateById(skill)
        }
    }

    void delete(BufferedReader br) {
        this.read()
        print "Informe o id da Skill:"
        Integer id = readIntegerInput(br, "id da Skill")
        SkillEntity skill = skillService.oneById(id)
        if(!skill){
            println "$id NÃO EXISTE"
        }else{
            skillService.deleteById(id)
        }
    }

    private String readInput(BufferedReader br, String prompt) {
        String input
        while (true) {
            print "$prompt: "
            input = br.readLine()
            if (!input.isEmpty()) {
                break
            }
            println "Campo obrigatório, por favor, preencha."
        }
        input
    }

    private Integer readIntegerInput(BufferedReader br, String prompt) {
        Integer input
        while (true) {
            print "$prompt: "
            try {
                input = Integer.parseInt(br.readLine())
                break
            } catch (NumberFormatException e) {
                println "Valor inválido, por favor, insira um número inteiro."
            }
        }
        input
    }

    private SkillEntity updateSkillDetails(BufferedReader br, SkillEntity skill) {
        print "Titulo da Competencia(${skill.title}):"
        String title = readInput(br, "Titulo da Competencia")
        skill.setTitle(title)

        print "Descrição da Competencia(${skill.description}):"
        String description = readInput(br, "Descrição da Competencia")
        skill.setDescription(description)
        skill
    }
}
