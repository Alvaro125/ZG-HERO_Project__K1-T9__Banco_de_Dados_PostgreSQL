package org.example.services

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class NaturalPersonService {

    static Connection db
    static SkillService skillService

    NaturalPersonService(Connection db){
        this.db = db
        this.skillService = new SkillService(db)
    }

    List<NaturalPersonEntity> listAll() {
        String sql = """SELECT np.cpf,np.age,p.email,p.description,p.password,p.name,p.id,
ad.country, ad.state, ad.cep 
FROM public.naturalpeople np 
INNER JOIN public.people p 
ON np.idPerson = p.id 
INNER JOIN public.address ad 
ON ad.id = p.address;""";
        ResultSet result = null;
        ArrayList<NaturalPersonEntity> list = new ArrayList();
        try {
            PreparedStatement comando = db.prepareStatement(sql);
            result = comando.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id")
                String email = result.getString("email")
                String name = result.getString("name")
                String password = result.getString("password")
                String description = result.getString("description")
                String cpf = result.getString("cpf")
                Integer age = result.getInt("age")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                List<SkillEntity> skills = skillService.listSkillsByPerson(id)
                list.add(new NaturalPersonEntity(name, email, password, description,
                        new AddressEntity(country,state,cep),
                cpf,age,id,skills))
            }
            result.close()
            comando.close()
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
        return list
    }

    NaturalPersonEntity oneById(Integer id) {
        String sql = """SELECT np.cpf, np.age,p.email,p.description,p.password,p.name,p.id,p.address,
ad.country, ad.state, ad.cep 
FROM public.naturalpeople np 
INNER JOIN public.people p 
ON np.idPerson = p.id 
INNER JOIN public.address ad 
ON ad.id = p.address WHERE p.id= ? LIMIT 1;"""
        NaturalPersonEntity person = null
        try {
            PreparedStatement command = db.prepareStatement(sql)
            command.setInt(1, id)
            ResultSet result = command.executeQuery()
            if (result != null && result.next()) {
                String email = result.getString("email")
                String name = result.getString("name")
                String password = result.getString("password")
                String description = result.getString("description")
                String cpf = result.getString("cpf")
                Integer age = result.getInt("age")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                Integer idAddress = result.getInt("address")
                List<SkillEntity> skills = skillService.listSkillsByPerson(id)
                AddressEntity address = new AddressEntity(country,state,cep)
                address.setId(idAddress)
                person = new NaturalPersonEntity(name, email, password, description,
                        address,
                        cpf,age,id,skills)
            }
            if (result != null) {
                result.close()
            }
            if (command != null) {
                command.close()
            }
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
        return person
    }

    def updateById(NaturalPersonEntity person) {
        try {
            String sql1 = """
UPDATE people 
SET name = ?, description = ?, email = ?, password = ?
WHERE id = ?;""";
            PreparedStatement command1 = db.prepareStatement(sql1);
            command1.setString(1, person.name);
            command1.setString(2, person.description);
            command1.setString(3, person.email);
            command1.setString(4, person.password);
            command1.setInt(5, person.id);
            command1.executeUpdate();
            command1.close();

            String sql2 = """
UPDATE address 
SET country = ?, state = ?, cep = ? 
WHERE id = ?;""";
            PreparedStatement command2 = db.prepareStatement(sql2);
            command2.setString(1, person.address.country);
            command2.setString(2, person.address.state);
            command2.setString(3, person.address.cep);
            command2.setInt(4, person.address.getId());
            command2.executeUpdate();
            command2.close();

            skillService.deleteSkillByPerson(person.id)
            skillService.addSkillByPerson(person.id, person.skills)

            String sql3 = """
UPDATE naturalpeople 
SET cpf = ? , age = ? 
WHERE idPerson = ? ;""";
            PreparedStatement command3 = db.prepareStatement(sql3);
            command3.setString(1, person.cpf);
            command3.setInt(2, person.age);
            command3.setInt(3, person.id);
            command3.execute();
            command3.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }

    def addUser(NaturalPersonEntity person) {
        try {
            Integer idAddress = -1
            String sql1 = "INSERT INTO address (country, state, cep) VALUES (?, ?, ?)"
            PreparedStatement command1 = db.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)
            command1.setString(1, person.address.getCountry())
            command1.setString(2, person.address.getState())
            command1.setString(3, person.address.getCep())
            command1.executeUpdate()
            ResultSet generatedKeys = command1.getGeneratedKeys()
            if (generatedKeys.next()) {
                idAddress = generatedKeys.getInt(1)
            }
            command1.close()

            Integer idPerson = -1
            String sql2 = "INSERT INTO people (email, name, description, address, password) VALUES (?, ?, ?, ?, ?)"
            PreparedStatement command2 = db.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)
            command2.setString(1, person.getEmail())
            command2.setString(2, person.getName())
            command2.setString(3, person.getDescription())
            command2.setInt(4, idAddress)
            command2.setString(5, person.getPassword())
            command2.executeUpdate()
            ResultSet generatedKeys2 = command2.getGeneratedKeys()
            if (generatedKeys2.next()) {
                idPerson = generatedKeys2.getInt(1)
            }
            command2.close()

            skillService.addSkillByPerson(idPerson, person.skills)
            String sql3 = "INSERT INTO naturalpeople (idPerson, cpf, age) VALUES (?, ?, ?)"
            PreparedStatement command3 = db.prepareStatement(sql3)
            command3.setInt(1, idPerson)
            command3.setString(2, person.cpf)
            command3.setInt(3, person.age)
            command3.execute()
            command3.close()
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
    }

    def deleteById(NaturalPersonEntity person) {
        try {
            skillService.deleteSkillByPerson(person.id)

            String sql1 = """DELETE FROM naturalpeople WHERE idPerson = ?;"""
            PreparedStatement command1 = db.prepareStatement(sql1)
            command1.setInt(1, person.id)
            command1.executeUpdate()
            command1.close()

            String sql2 = """DELETE FROM people WHERE id = ?;"""
            PreparedStatement command2 = db.prepareStatement(sql2)
            command2.setInt(1, person.id)
            command2.executeUpdate()
            command2.close()

            String sql3 = """DELETE FROM address WHERE id = ?;"""
            PreparedStatement command3 = db.prepareStatement(sql3)
            command3.setInt(1, person.address.getId())
            command3.execute()
            command3.close()
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace()
        }
    }
}
