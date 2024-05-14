package org.example.services.impl


import org.example.dao.AddressDao
import org.example.dao.JobDao
import org.example.dao.LegalPersonDao
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.services.JobsService

class JobsServiceImpl implements JobsService{
    static JobDao jobDao
    static LegalPersonDao legalPersonDao
    static AddressDao addressDao

    JobsServiceImpl(JobDao jobDao, LegalPersonDao legalPersonDao, AddressDao addressDao){
        this.legalPersonDao = legalPersonDao
        this.addressDao = addressDao
        this.jobDao = jobDao
    }

    List<JobEntity> listAll() {
        return jobDao.getAll().collect {job->
            {
                LegalPersonEntity legalPerson = legalPersonDao.getById(job.person.id)
                job.setPerson(legalPerson)
                return job
            }}.toList()
    }
    List<JobEntity> listByPerson(Integer idPerson) {
        return jobDao.getByIdPerson(idPerson)
    }
    JobEntity oneById(Integer id) {
        return jobDao.getById(id)
    }
    void updateById(JobEntity job) {
        jobDao.updateById(job)
        addressDao.updateById(job.local)
    }
    void addJob(JobEntity job) {
        job.local.id = addressDao.create(job.local).getId()
        jobDao.create(job)
    }
    def deleteById(JobEntity job) {
        jobDao.deleteById(job.id)
        addressDao.deleteById(job.local.id)
    }
}
