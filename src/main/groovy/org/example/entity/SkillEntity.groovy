package org.example.entity


class SkillEntity {
    String title
    String description
    Integer id
    SkillEntity(String title, String description, Integer id = 0){
        this.title = title
        this.description = description
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    @Override
    String toString() {
        return """${id}-${title}
    Descrição: ${description}"""
    }
}
