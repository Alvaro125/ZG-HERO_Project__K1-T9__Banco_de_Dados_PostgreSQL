package org.example.ui

import org.example.config.Database
import org.example.entity.SkillEntity
import org.example.services.SkillService

class SkillsUI {
    private static SkillService skillService
    SkillsUI(){
        skillService = new SkillService(Database.conn)
    }
    void read() {
        println """
@@Lista de Competencias@@@@@@@@@@@@@@@@@@@@@@@@@@@@
${skillService.listSkills().join("\n")}
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        print "Titulo da Competencia:"
        String title = br.readLine()
        while (title.isEmpty()){
            print "Titulo da Competencia:"
            title = br.readLine()
        }
        print "Descrição da Competencia:"
        String description = br.readLine()
        while (description.isEmpty()){
            print "Descrição da Competencia:"
            description = br.readLine()
        }
        skillService.addSkill(title, description)
    }

    void update(BufferedReader br) {
        this.read()
        print "Informe o id da Skill:"
        Integer id = br.readLine().toInteger()

        SkillEntity skill = skillService.oneById(id)
        if(!skill){
            println "$id NÃO EXISTE"
        }else{
            print "Titulo da Competencia(${skill.title}):"
            String title = br.readLine()
            if (!title.isEmpty()){
                skill.setTitle(title)
            }
            print "Descrição da Competencia(${skill.description}):"
            String description = br.readLine()
            if (!description.isEmpty()){
                skill.setDescription(description)
            }
            skillService.updateById(skill)
        }
    }

    void delete(BufferedReader br) {
        this.read()
        print "Informe o id da Skill:"
        Integer id = br.readLine().toInteger()

        SkillEntity skill = skillService.oneById(id)
        if(!skill){
            println "$id NÃO EXISTE"
        }else{
            skillService.deleteById(id)
        }
    }
}
