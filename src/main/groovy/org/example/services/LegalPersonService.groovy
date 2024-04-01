package org.example.services

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.PersonEntity
import org.example.entity.SkillEntity
import org.example.interfaces.IPerson

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class LegalPersonService{

    static Connection db
    static SkillService skillService

    LegalPersonService(Connection db){
        this.db = db
        this.skillService = new SkillService(db)
    }

    List<LegalPersonEntity> listAll() {
        String sql = """SELECT lp.cnpj,p.email,p.description,p.password,p.name,p.id,
ad.country, ad.state, ad.cep 
FROM public.legalpeople lp 
INNER JOIN public.people p 
ON lp."idPerson" = p.id 
INNER JOIN public.address ad 
ON ad.id = p.address;""";
        ResultSet result = null;
        ArrayList<LegalPersonEntity> list = new ArrayList();
        try {
            PreparedStatement comando = Database.conn.prepareStatement(sql);
            result = comando.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id")
                String email = result.getString("email")
                String name = result.getString("name")
                String password = result.getString("password")
                String description = result.getString("description")
                String cnpj = result.getString("cnpj")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                List<SkillEntity> skills = skillService.listSkillsByPerson(id)
                list.add(new LegalPersonEntity(name, email, password, description,
                        new AddressEntity(country,state,cep),
                cnpj,id,skills))
            }
            result.close()
            comando.close()
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
        return list
    }

    LegalPersonEntity onebyId(Integer id) {
        String sql = """SELECT lp.cnpj,p.email,p.description,p.password,p.name,p.id,p.address,
ad.country, ad.state, ad.cep 
FROM public.legalpeople lp 
INNER JOIN public.people p 
ON lp."idPerson" = p.id 
INNER JOIN public.address ad 
ON ad.id = p.address WHERE p.id= ? LIMIT 1;"""
        LegalPersonEntity person = null
        try {
            PreparedStatement command = Database.conn.prepareStatement(sql)
            command.setInt(1, id)
            ResultSet result = command.executeQuery()
            if (result != null && result.next()) {
                String email = result.getString("email")
                String name = result.getString("name")
                String password = result.getString("password")
                String description = result.getString("description")
                String cnpj = result.getString("cnpj")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                Integer idAddress = result.getInt("address")
                List<SkillEntity> skills = skillService.listSkillsByPerson(id)
                AddressEntity address = new AddressEntity(country,state,cep)
                address.setId(idAddress)
                person = new LegalPersonEntity(name, email, password, description,
                        address,
                        cnpj,id,skills)
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

    def updateById(LegalPersonEntity person) {
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
UPDATE legalpeople 
SET cnpj = ? 
WHERE "idPerson" = ? ;""";
            PreparedStatement command3 = db.prepareStatement(sql3);
            command3.setString(1, person.cnpj);
            command3.setInt(2, person.id);
            command3.execute();
            command3.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }

    void addUser(LegalPersonEntity person) {
        try {
            Integer idAddress = -1
            Integer idPerson = -1
            String sql1 = """INSERT INTO public.address ("country", "state", "cep") 
            VALUES (?, ?, ?) RETURNING id;""";
            PreparedStatement command1 = db.prepareStatement(sql1)
            command1.setString(1, person.address.getCountry());
            command1.setString(2, person.address.getState());
            command1.setString(3, person.address.getCep());
            ResultSet result1 = command1.executeQuery();
            if (result1 != null && result1.next()) {
                idAddress = result1.getInt("id")
            }
            command1.close();
            String sql2 = """INSERT INTO people 
(email, name, description, address, password) 
                    VALUES (?, ?, ?, ?, ?) RETURNING id;""";
            PreparedStatement command2 = db.prepareStatement(sql2)
            command2.setString(1, person.getEmail());
            command2.setString(2, person.getName());
            command2.setString(3, person.getDescription());
            command2.setInt(4, idAddress);
            command2.setString(5, person.getPassword());
            ResultSet result2 = command2.executeQuery();
            if (result2 != null && result2.next()) {
                idPerson = result2.getInt("id")
            }
            command2.close();
            skillService.addSkillByPerson(idPerson,person.skills)
            String sql3 = """INSERT INTO legalpeople ("idPerson", "cnpj") 
                    VALUES (?, ?);""";
            PreparedStatement command3 = db.prepareStatement(sql3)
            command3.setInt(1, idPerson);
            command3.setString(2, person.getCnpj());
            command3.execute()
            command3.close();

        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }
    def deleteById(LegalPersonEntity person) {
        try {
            skillService.deleteSkillByPerson(person.id)

            String sql1 = """DELETE FROM legalpeople WHERE "idPerson" = ?;"""
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
