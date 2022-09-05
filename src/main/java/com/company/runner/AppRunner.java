package com.company.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.company.entity.EligibiltyReport;
import com.company.repo.EligibilityReportsRepo;

@Component
public class AppRunner implements ApplicationRunner {

	@Autowired
	private EligibilityReportsRepo repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		EligibiltyReport entity1 = new EligibiltyReport();

		entity1.setEliId(1);
		entity1.setName("Priya");
		entity1.setEmail("priya.k@company.com");
		entity1.setMobNo(9874564523l);
		entity1.setGender('F');
		entity1.setSsn(55548484844l);
		entity1.setPlanName("Health");
		entity1.setPlanStatus("Rejected");

		repo.save(entity1);

		EligibiltyReport entity2 = new EligibiltyReport();

		entity2.setEliId(2);
		entity2.setName("sasha");
		entity2.setEmail("sashacole@company.com");
		entity2.setMobNo(8777645230l);
		entity2.setGender('F');
		entity2.setSsn(47882848447l);
		entity2.setPlanName("LifeTime");
		entity2.setPlanStatus("Approved");

		repo.save(entity2);

		EligibiltyReport entity3 = new EligibiltyReport();

		entity3.setEliId(3);
		entity3.setName("Akash");
		entity3.setEmail("aakash@company.com");
		entity3.setMobNo(6887845230l);
		entity3.setGender('M');
		entity3.setSsn(55468484822l);
		entity3.setPlanName("SNAP");
		entity3.setPlanStatus("Rejected");

		repo.save(entity3);

		EligibiltyReport entity4 = new EligibiltyReport();

		entity4.setEliId(4);
		entity4.setName("Rohit");
		entity4.setEmail("rohitshah@company.com");
		entity4.setMobNo(7822355230l);
		entity4.setGender('M');
		entity4.setSsn(65558414844l);
		entity4.setPlanName("Health");
		entity4.setPlanStatus("Approved");

		repo.save(entity4);

		EligibiltyReport entity5 = new EligibiltyReport();

		entity5.setEliId(5);
		entity5.setName("John");
		entity5.setEmail("johncena@company.com");
		entity5.setMobNo(8797852258l);
		entity5.setGender('M');
		entity5.setSsn(54445522794l);
		entity5.setPlanName("MediCare");
		entity5.setPlanStatus("Closed");

		repo.save(entity5);
	}

}
