package org.example.dao.impl

import org.example.dao.JobDao
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class JobDaoImpl implements JobDao{
    private Connection db
    JobDaoImpl(Connection db){
        this.db = db
    }

    @Override
    List<JobEntity> getAll() {
        String sql = sqlQueryGetAll()
        ResultSet result = null;
        PreparedStatement command = null
        ArrayList<JobEntity> list = new ArrayList();
        try {
            command = db.prepareStatement(sql);
            result = command.executeQuery();
            while (result.next()) {
                list.add(createJobEntity(result))
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return list
    }
    private String sqlQueryGetAll(){
        return """SELECT jobs.id, jobs."idLegalPerson" ,jobs.name,jobs.description,
jobs.local,address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs.local; """
    }

    @Override
    List<JobEntity> getByIdPerson(Integer idPerson) {
        String sql = sqlQueryGetByIdPerson()
        ResultSet result = null;
        PreparedStatement command = null
        ArrayList<JobEntity> list = new ArrayList();
        try {
            command = db.prepareStatement(sql);
            command.setInt(1, idPerson)
            result = command.executeQuery();
            while (result.next()) {
                list.add(createJobEntity(result))
            }
        } catch (SQLException exception_sql) {
            exception_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return list
    }
    private String sqlQueryGetByIdPerson(){
        return """SELECT jobs.id, jobs."idLegalPerson" ,jobs.name,jobs.description,
jobs."local",address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs."local" WHERE jobs."idLegalPerson" = ?; """
    }

    @Override
    JobEntity getById(Integer id) {
        String sql = sqlQueryGetById()
        JobEntity job = null
        ResultSet result = null;
        PreparedStatement command = null
        try {
            command = db.prepareStatement(sql)
            command.setInt(1, id)
            result = command.executeQuery()
            if (result != null && result.next()) {
                job = createJobEntity(result)
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return job
    }
    private String sqlQueryGetById(){
        return """SELECT jobs.id, jobs.idLegalPerson ,jobs.name,jobs.description,
jobs.local,address.country,address.state,address.cep FROM jobs
INNER JOIN address
ON address.id = jobs.local 
WHERE jobs.id=? LIMIT 1;"""
    }

    @Override
    void updateById(JobEntity job) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlUpdate()
            command = db.prepareStatement(sql)
            command.setString(1, job.name);
            command.setString(2, job.description);
            command.setInt(3, job.id);
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlUpdate(){
        return "UPDATE jobs SET name = ?, description = ? WHERE id = ?;"
    }

    @Override
    JobEntity create(JobEntity job) {
        JobEntity newJob = job
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlCreate()
            command = db.prepareStatement(sql)
            command.setString(1, newJob.name);
            command.setString(2, newJob.description);
            command.setInt(3, newJob.local.id);
            command.setInt(4, newJob.person.id);
            command.executeUpdate()
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
        return newJob
    }
    private String sqlCreate(){
        return "INSERT INTO jobs (name, description, local, \"idLegalPerson\") VALUES (?, ?, ?, ?)"
    }

    @Override
    void deleteById(Integer id) {
        ResultSet result = null
        PreparedStatement command = null
        try {
            String sql = sqlDelete()
            command = db.prepareStatement(sql)
            command.setInt(1, id);
            command.executeUpdate();
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            closeResultSetAndStatement(result, command)
        }
    }
    private String sqlDelete(){
        return "DELETE FROM jobs WHERE id = ?;"
    }

    private JobEntity createJobEntity(ResultSet result) throws SQLException {
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
        LegalPersonEntity legalPerson = new LegalPersonEntity()
        legalPerson.setId(idPerson)
        return new JobEntity(name, description, address, legalPerson,id)
    }

    private void closeResultSetAndStatement(ResultSet result, PreparedStatement statement) {
        try {
            if (result != null) {
                result.close()
            }
        } catch (SQLException excecao_sql) {
            excecao_sql.printStackTrace()
        } finally {
            try {
                if (statement != null) {
                    statement.close()
                }
            } catch (SQLException excecao_sql) {
                excecao_sql.printStackTrace()
            }
        }
    }
}
