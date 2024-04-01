package org.example.services

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.ui.JobsUI

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class JobsService {

    static Connection db
    static SkillService skillService
    static LegalPersonService legalPersonService

    JobsService(Connection db){
        this.db = db
        this.skillService = new SkillService(db)
        this.legalPersonService = new LegalPersonService(db)
    }

    List<JobEntity> listAll() {
        String sql = """SELECT jobs.id, jobs."idLegalPerson" ,jobs.name,jobs.description,
jobs."local",address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs."local"; """;
        ResultSet result = null;
        ArrayList<JobEntity> list = new ArrayList();
        try {
            PreparedStatement comando = Database.conn.prepareStatement(sql);
            result = comando.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id")
                Integer idPerson = result.getInt("idLegalPerson")
                Integer local = result.getInt("local")
                String name = result.getString("name")
                String description = result.getString("description")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                AddressEntity address = new AddressEntity(country,state,cep)
                address.setId(local)
                list.add(new JobEntity(name, description,
                        address,
                legalPersonService.onebyId(idPerson),id))
            }
            result.close()
            comando.close()
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
        return list
    }

    List<JobEntity> listByPerson(Integer idPerson) {
        String sql = """SELECT jobs.id, jobs."idLegalPerson" ,jobs.name,jobs.description,
jobs."local",address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs."local" WHERE jobs."idLegalPerson" = ?; """;
        ResultSet result = null;
        ArrayList<JobEntity> list = new ArrayList();
        try {
            PreparedStatement comando = Database.conn.prepareStatement(sql);
            comando.setInt(1, idPerson)
            result = comando.executeQuery();
            while (result.next()) {
                Integer id = result.getInt("id")
                Integer local = result.getInt("local")
                String name = result.getString("name")
                String description = result.getString("description")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                AddressEntity address = new AddressEntity(country,state,cep)
                address.setId(local)
                list.add(new JobEntity(name, description,
                        address,
                        legalPersonService.onebyId(idPerson),id))
            }
            result.close()
            comando.close()
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace()
        }
        return list
    }

    JobEntity onebyId(Integer id) {
        String sql = """SELECT jobs.id, jobs."idLegalPerson" ,jobs.name,jobs.description,
jobs."local",address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs."local" 
WHERE jobs.id=? LIMIT 1;"""
        JobEntity job = null
        try {
            PreparedStatement command = Database.conn.prepareStatement(sql)
            command.setInt(1, id)
            ResultSet result = command.executeQuery()
            if (result != null && result.next()) {
                Integer idPerson = result.getInt("idLegalPerson")
                Integer local = result.getInt("local")
                String name = result.getString("name")
                String description = result.getString("description")
                String country = result.getString("country")
                String cep = result.getString("cep")
                String state = result.getString("state")
                AddressEntity address = new AddressEntity(country,state,cep)
                address.setId(local)
                job = new JobEntity(name, description,
                        address,
                        legalPersonService.onebyId(idPerson),id)
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
        return job
    }

    def updateById(JobEntity job) {
        try {
            String sql1 = """
UPDATE jobs 
SET name = ?, description = ? 
WHERE id = ?;""";
            PreparedStatement command1 = db.prepareStatement(sql1);
            command1.setString(1, job.name);
            command1.setString(2, job.description);
            command1.setInt(3, job.id);
            command1.executeUpdate();
            command1.close();

            String sql2 = """
UPDATE address 
SET country = ?, state = ?, cep = ? 
WHERE id = ?;""";
            PreparedStatement command2 = db.prepareStatement(sql2);
            command2.setString(1, job.local.country);
            command2.setString(2, job.local.state);
            command2.setString(3, job.local.cep);
            command2.setInt(4, job.local.getId());
            command2.execute();
            command2.close();
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace();
        }
    }

    void addJob(JobEntity job) {
        try {
            Integer idAddress = -1
            String sql1 = """INSERT INTO public.address ("country", "state", "cep") 
            VALUES (?, ?, ?) RETURNING id;""";
            PreparedStatement command1 = db.prepareStatement(sql1)
            command1.setString(1, job.local.getCountry());
            command1.setString(2, job.local.getState());
            command1.setString(3, job.local.getCep());
            ResultSet result1 = command1.executeQuery();
            if (result1 != null && result1.next()) {
                idAddress = result1.getInt("id")
            }
            command1.close();
            String sql2 = """INSERT INTO jobs 
(name, description, local, "idLegalPerson") 
                    VALUES (?, ?, ?, ?)""";
            PreparedStatement command2 = db.prepareStatement(sql2)
            command2.setString(1, job.getName());
            command2.setString(2, job.getDescription());
            command2.setInt(3, idAddress);
            command2.setInt(4, job.person.getId());
            command2.execute();
            command2.close();

        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }
    def deleteById(JobEntity job) {
        try {

            String sql1 = """DELETE FROM jobs WHERE id = ?;"""
            PreparedStatement command1 = db.prepareStatement(sql1)
            command1.setInt(1, job.getId())
            command1.executeUpdate()
            command1.close()

            String sql3 = """DELETE FROM address WHERE id = ?;"""
            PreparedStatement command3 = db.prepareStatement(sql3)
            command3.setInt(1, job.local.getId())
            command3.execute()
            command3.close()
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace()
        }
    }
}
