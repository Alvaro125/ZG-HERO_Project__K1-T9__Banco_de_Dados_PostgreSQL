package org.example.services

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

public class SkillService {
    static Connection db

    SkillService(Connection db){
        this.db = db
    }


    void addSkill(String title, String description) {
        try {
            String sql = "INSERT INTO skills(title, description) VALUES (?,?)";
            PreparedStatement command = db.prepareStatement(sql)
            command.setString(1, title);
            command.setString(2, description)
            command.executeUpdate();
            command.close();
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }

    void addSkillByPerson(Integer idPerson, List<SkillEntity> skills) {
        try {
            String sql = """INSERT INTO skills_people ("skills_id", "people_id") VALUES (?,?)""";

            for (SkillEntity skill : skills) {
                PreparedStatement command = db.prepareStatement(sql);
                command.setInt(1, skill.getId());
                command.setInt(2, idPerson);
                command.executeUpdate();
                command.close();
            }
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }

    void deleteSkillByPerson(Integer idPerson) {
        try {
            String sql = """DELETE FROM skills_people WHERE people_id = ?""";
            PreparedStatement command = db.prepareStatement(sql);
            command.setInt(1, idPerson);
            command.executeUpdate();
            command.close();
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }


    List<SkillEntity> listSkills() {
        String sql = """
SELECT * FROM public.skills
ORDER BY id ASC;""";
        ResultSet list_results = null;
        ArrayList<SkillEntity> list = new ArrayList();
        try {
            PreparedStatement command = db.prepareStatement(sql);
            list_results = command.executeQuery();
            while (list_results.next()) {
                Integer id = list_results.getInt("id")
                String title = list_results.getString("title")
                String description = list_results.getString("description")
                list.add(new SkillEntity(title,description,id))
            }
            list_results.close()
            command.close()
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace()
        }
        return list
    }

    List<SkillEntity> listSkillsByPerson(Integer idPerson) {
        String sql = """
SELECT sk.title, sk.description, sk.id FROM public.skills_people sp
INNER JOIN public.skills sk ON sk.id = sp.skills_id
WHERE sp.people_id = ?;""";
        ResultSet list_results = null;
        ArrayList<SkillEntity> list = new ArrayList();
        try {
            PreparedStatement command = db.prepareStatement(sql);
            command.setInt(1,idPerson)
            list_results = command.executeQuery();
            while (list_results.next()) {
                Integer id = list_results.getInt("id")
                String title = list_results.getString("title")
                String description = list_results.getString("description")
                list.add(new SkillEntity(title,description,id))
            }
            list_results.close()
            command.close()
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace()
        }
        return list
    }

    SkillEntity oneById(Integer id) {
        String sql = """
    SELECT * FROM skills
    WHERE skills.id = ? 
    LIMIT 1;""";
        SkillEntity skill = null;
        try {
            PreparedStatement command = db.prepareStatement(sql)
            command.setInt(1, id)
            ResultSet result = command.executeQuery()
            if (result != null && result.next()) {
                String title = result.getString("title");
                String description = result.getString("description");
                skill = new SkillEntity(title, description, id);
            }
            if (result != null) {
                result.close();
            }
            if (command != null) {
                command.close();
            }
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
        return skill;
    }

    def updateById(SkillEntity skill) {
        String sql = """
UPDATE skills 
SET title = ?, description = ? 
WHERE id = ?;""";
        try {
            PreparedStatement command = db.prepareStatement(sql);
            command.setString(1, skill.title);
            command.setString(2, skill.description);
            command.setInt(3, skill.id);
            command.executeUpdate();
            command.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }

    def deleteAllbyPerson(Integer id) {
        String sql = "DELETE FROM skills_people WHERE skills_id = ?";
        try {
            PreparedStatement command = db.prepareStatement(sql);
            command.setInt(1, id);
            command.executeUpdate();
            command.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }

    def deleteById(Integer id) {
        this.deleteAllbyPerson(id)
        String sql = "DELETE FROM skills WHERE id = ?";
        try {
            PreparedStatement command = db.prepareStatement(sql);
            command.setInt(1, id);
            command.executeUpdate();
            command.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }
}
