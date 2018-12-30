package it.unisalento.se.saw.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.se.saw.Iservices.ISchedulerService;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.Day;
import it.unisalento.se.saw.domain.DegreeCourse;
import it.unisalento.se.saw.domain.Lesson;
import it.unisalento.se.saw.domain.Scheduler;
import it.unisalento.se.saw.domain.Subject;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.TypeLesson;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.CourseTypeDTO;
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.DegreeCourseDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.SubjectDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.TypeDegreeCourseDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.repositories.LessonRepository;
import it.unisalento.se.saw.repositories.SchedulerRepository;
import it.unisalento.se.saw.repositories.TermRepository;
import it.unisalento.se.saw.repositories.TypeLessonRepository;

@Service
public class SchedulerService implements ISchedulerService {
	
	@Autowired
	SchedulerRepository schedulerRepository;
	
	@Autowired
	TypeLessonRepository typeLessonRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	TermRepository termRepository;
	
	public SchedulerDTO save(SchedulerDTO schedulerDTO) {
		
		if(schedulerDTO.getIdScheduler() == 0) {
			Scheduler scheduler = new Scheduler();
			scheduler.setIdscheduler(schedulerDTO.getIdScheduler());
			
			DegreeCourse course = new DegreeCourse();
			course.setIddegreeCourse(schedulerDTO.getDegreeCourse().getIdcourse());
			
			Term term = new Term();
			term.setIdterm(schedulerDTO.getTerm().getIdterm());
			
			scheduler.setDegreeCourse(course);
			scheduler.setTerm(term);
			
			Scheduler newScheduler = schedulerRepository.save(scheduler);
			
			for (int i=0; i<schedulerDTO.getTypeLessons().size(); i++) {
				TypeLesson typeLesson = new TypeLesson();
				typeLesson.setStart(schedulerDTO.getTypeLessons().get(i).getStart());
				typeLesson.setEnd(schedulerDTO.getTypeLessons().get(i).getEnd());
				
				Day day = new Day();
				day.setIdday(schedulerDTO.getTypeLessons().get(i).getDay().getIdDay());
				
				typeLesson.setDay(day);
				
				Classroom classroom = new Classroom();
				classroom.setIdclassroom(schedulerDTO.getTypeLessons().get(i).getClassroom().getId());
				
				typeLesson.setClassroom(classroom);
				typeLesson.setScheduler(newScheduler);
				
				Subject subject = new Subject();
				subject.setIdsubject(schedulerDTO.getTypeLessons().get(i).getSubject().getId());
				
				typeLesson.setSubject(subject);
				TypeLesson newTypeLesson = typeLessonRepository.save(typeLesson);
				
				
				List<Date> days = getEachDayBetweenDates(schedulerDTO.getTypeLessons().get(i).getDay().getIdDay(), schedulerDTO.getTerm().getStart(), schedulerDTO.getTerm().getEnd());

				for(int j=0; j<days.size(); j++) {					
					Lesson lesson = new Lesson(); //insert
					lesson.setStart(joinDateAndTime(days.get(j), schedulerDTO.getTypeLessons().get(i).getStart()));
					lesson.setEnd(joinDateAndTime(days.get(j), schedulerDTO.getTypeLessons().get(i).getEnd()));
					lesson.setClassroom(classroom);
					lesson.setTypeLesson(newTypeLesson);
					
					lessonRepository.save(lesson);
				}
				
			}
			
			SchedulerDTO newSchedulerDTO = new SchedulerDTO();
			newSchedulerDTO.setIdScheduler(newScheduler.getIdscheduler());
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(newScheduler.getDegreeCourse().getIddegreeCourse());
			
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			List<Term> terms = termRepository.getByAcademicYear(newScheduler.getDegreeCourse().getAcademicYear().getIdacademicYear());
			for(int k=0; k<terms.size(); k++) {
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(terms.get(k).getIdterm());
				termDTO.setStart(terms.get(k).getStart());
				termDTO.setEnd(terms.get(k).getEnd());
				termDTOs.add(termDTO);
			}
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(newScheduler.getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(newScheduler.getDegreeCourse().getAcademicYear().getYear());
			academicYearDTO.setTerms(termDTOs);
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(newScheduler.getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(newScheduler.getDegreeCourse().getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			
			newSchedulerDTO.setDegreeCourse(degreeCourseDTO);
			
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(newScheduler.getTerm().getIdterm());
			termDTO.setStart(newScheduler.getTerm().getStart());
			termDTO.setEnd(newScheduler.getTerm().getEnd());
			
			newSchedulerDTO.setTerm(termDTO);
			
			List<TypeLesson> typeLessons = typeLessonRepository.getTypeLessonsOfScheduler(newScheduler.getIdscheduler());
			List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
			
			for(int i=0; i<typeLessons.size(); i++) {
				TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
				typeLessonDTO.setIdtypeLesson(typeLessons.get(i).getIdtypeLesson());
				typeLessonDTO.setStart(typeLessons.get(i).getStart());
				typeLessonDTO.setEnd(typeLessons.get(i).getEnd());
				
				DayDTO dayDTO = new DayDTO();
				dayDTO.setIdDay(typeLessons.get(i).getDay().getIdday());
				dayDTO.setName(typeLessons.get(i).getDay().getName());
				
				typeLessonDTO.setDay(dayDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(typeLessons.get(i).getSubject().getIdsubject());
				subjectDTO.setName(typeLessons.get(i).getSubject().getTypeSubject().getName());
				
				typeLessonDTO.setSubject(subjectDTO);
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(typeLessons.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(typeLessons.get(i).getClassroom().getName());
				
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(typeLessons.get(i).getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(typeLessons.get(i).getClassroom().getBuilding().getName());
				
				classroomDTO.setBuilding(buildingDTO);
				
				typeLessonDTO.setClassroom(classroomDTO);
				
				typeLessonDTOs.add(typeLessonDTO);
			}
			
			newSchedulerDTO.setTypeLessons(typeLessonDTOs);
			
			return newSchedulerDTO;
		} else {
			for (int i=0; i<schedulerDTO.getTypeLessons().size(); i++) {
				TypeLesson typeLesson = new TypeLesson();
				typeLesson.setIdtypeLesson(schedulerDTO.getTypeLessons().get(i).getIdtypeLesson());
				
				typeLesson.setStart(schedulerDTO.getTypeLessons().get(i).getStart());
				typeLesson.setEnd(schedulerDTO.getTypeLessons().get(i).getEnd());
				
				Scheduler scheduler = new Scheduler();
				scheduler.setIdscheduler(schedulerDTO.getIdScheduler());
				typeLesson.setScheduler(scheduler);
				
				Day day = new Day();
				day.setIdday(schedulerDTO.getTypeLessons().get(i).getDay().getIdDay());
				
				typeLesson.setDay(day);
				
				Classroom classroom = new Classroom();
				classroom.setIdclassroom(schedulerDTO.getTypeLessons().get(i).getClassroom().getId());
				
				typeLesson.setClassroom(classroom);
				
				Subject subject = new Subject();
				subject.setIdsubject(schedulerDTO.getTypeLessons().get(i).getSubject().getId());
				
				typeLesson.setSubject(subject);
				
				TypeLesson newTypeLesson = typeLessonRepository.save(typeLesson);
				
				lessonRepository.deleteByTypeLesson(typeLesson.getIdtypeLesson());
				
				List<Date> days = getEachDayBetweenDates(schedulerDTO.getTypeLessons().get(i).getDay().getIdDay(), schedulerDTO.getTerm().getStart(), schedulerDTO.getTerm().getEnd());

				for(int j=0; j<days.size(); j++) {					
					Lesson lesson = new Lesson(); //insert
					lesson.setStart(joinDateAndTime(days.get(j), schedulerDTO.getTypeLessons().get(i).getStart()));
					lesson.setEnd(joinDateAndTime(days.get(j), schedulerDTO.getTypeLessons().get(i).getEnd()));
					lesson.setClassroom(classroom);
					lesson.setTypeLesson(newTypeLesson);
					
					lessonRepository.save(lesson);
				}
				
			}
			
			Scheduler newScheduler = schedulerRepository.getOne(schedulerDTO.getIdScheduler());
			
			SchedulerDTO newSchedulerDTO = new SchedulerDTO();
			newSchedulerDTO.setIdScheduler(newScheduler.getIdscheduler());
			
			DegreeCourseDTO degreeCourseDTO = new DegreeCourseDTO();
			degreeCourseDTO.setIdcourse(newScheduler.getDegreeCourse().getIddegreeCourse());
			
			List<TermDTO> termDTOs= new ArrayList<TermDTO>();
			List<Term> terms = termRepository.getByAcademicYear(newScheduler.getDegreeCourse().getAcademicYear().getIdacademicYear());
			for(int k=0; k<terms.size(); k++) {
				TermDTO termDTO = new TermDTO();
				termDTO.setIdterm(terms.get(k).getIdterm());
				termDTO.setStart(terms.get(k).getStart());
				termDTO.setEnd(terms.get(k).getEnd());
				termDTOs.add(termDTO);
			}
			AcademicYearDTO academicYearDTO = new AcademicYearDTO();
			academicYearDTO.setIdacademicYear(newScheduler.getDegreeCourse().getAcademicYear().getIdacademicYear());
			academicYearDTO.setYear(newScheduler.getDegreeCourse().getAcademicYear().getYear());
			academicYearDTO.setTerms(termDTOs);
			degreeCourseDTO.setAcademicYear(academicYearDTO);
			
			TypeDegreeCourseDTO typeDegreeCourse = new TypeDegreeCourseDTO();
			typeDegreeCourse.setIdtypeDegreeCourse(newScheduler.getDegreeCourse().getTypeDegreeCourse().getIdtypeDegreeCourse());
			typeDegreeCourse.setName(newScheduler.getDegreeCourse().getTypeDegreeCourse().getName());
			CourseTypeDTO courseType = new CourseTypeDTO();
			courseType.setIdcourseType(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getIdcourseType());
			courseType.setCfu(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getCfu());
			courseType.setDescription(newScheduler.getDegreeCourse().getTypeDegreeCourse().getCourseType().getDescription());
			typeDegreeCourse.setCourseType(courseType);
			degreeCourseDTO.setTypeDegreeCourse(typeDegreeCourse);
			
			newSchedulerDTO.setDegreeCourse(degreeCourseDTO);
			
			TermDTO termDTO = new TermDTO();
			termDTO.setIdterm(newScheduler.getTerm().getIdterm());
			termDTO.setStart(newScheduler.getTerm().getStart());
			termDTO.setEnd(newScheduler.getTerm().getEnd());
			
			newSchedulerDTO.setTerm(termDTO);
			
			List<TypeLesson> typeLessons = typeLessonRepository.getTypeLessonsOfScheduler(newScheduler.getIdscheduler());
			List<TypeLessonDTO> typeLessonDTOs = new ArrayList<TypeLessonDTO>();
			
			for(int i=0; i<typeLessons.size(); i++) {
				TypeLessonDTO typeLessonDTO = new TypeLessonDTO();
				typeLessonDTO.setIdtypeLesson(typeLessons.get(i).getIdtypeLesson());
				typeLessonDTO.setStart(typeLessons.get(i).getStart());
				typeLessonDTO.setEnd(typeLessons.get(i).getEnd());
				
				DayDTO dayDTO = new DayDTO();
				dayDTO.setIdDay(typeLessons.get(i).getDay().getIdday());
				dayDTO.setName(typeLessons.get(i).getDay().getName());
				
				typeLessonDTO.setDay(dayDTO);
				
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(typeLessons.get(i).getSubject().getIdsubject());
				subjectDTO.setName(typeLessons.get(i).getSubject().getTypeSubject().getName());
				
				typeLessonDTO.setSubject(subjectDTO);
				
				ClassroomDTO classroomDTO = new ClassroomDTO();
				classroomDTO.setId(typeLessons.get(i).getClassroom().getIdclassroom());
				classroomDTO.setName(typeLessons.get(i).getClassroom().getName());
				
				BuildingDTO buildingDTO = new BuildingDTO();
				buildingDTO.setId(typeLessons.get(i).getClassroom().getBuilding().getIdbuilding());
				buildingDTO.setName(typeLessons.get(i).getClassroom().getBuilding().getName());
				
				classroomDTO.setBuilding(buildingDTO);
				
				typeLessonDTO.setClassroom(classroomDTO);
				
				typeLessonDTOs.add(typeLessonDTO);
			}
			
			newSchedulerDTO.setTypeLessons(typeLessonDTOs);
			
			return newSchedulerDTO;
		}
	}
	
	public List<Date> getEachDayBetweenDates(int day, Date start, Date end) {
		
		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		List<Date> days = new ArrayList<Date>();
		
		boolean reachedTheDay = false;

		while (startDate.isBefore(endDate.plusDays(1))){
		    if ( startDate.getDayOfWeek() == day ){
		    	if(this.isBusinessDay(startDate.toDate())) {
		    		days.add(startDate.toDate());
		    	}
		        if(!reachedTheDay) {
		        	reachedTheDay = true;
		        }
		    }
		    if(reachedTheDay) {
		    	startDate = startDate.plusDays(7);
		    } else {
		    	startDate = startDate.plusDays(1);
		    }
		    
		}
		return days;
	}
	
	public Integer periodHasScheduler(int idterm, int idcourse) {
		return schedulerRepository.schedulerExists(idterm, idcourse);
	}
	
	public Date joinDateAndTime(Date data, Date time) {
		String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(data);
		String startingTime = new SimpleDateFormat("HH:mm:ss").format(time);
		LocalDate datePart = LocalDate.parse(startingDate);
	    LocalTime timePart = LocalTime.parse(startingTime);
	    LocalDateTime dt = LocalDateTime.of(datePart, timePart);
	    return Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public boolean isBusinessDay(Date date){
		
		
		// check Labor Day (1st May)
		if (date.getMonth() == 5 && date.getDay() == 1) {
			return false;
		}
		
		// check liberation day
		if (date.getMonth() == 4 && date.getDay() == 25) {
			return false;
		}
		
		
		// check if all saints
		if (date.getMonth() == 11 && date.getDay() == 1) {
			return false;
		}
		
		// check if immaculate
		if (date.getMonth() == 12 && date.getDay() == 8) {
			return false;
		}
		
		//check if sessione invernale
		if ((date.getMonth() == 12 && date.getDay() >= 24) || (date.getMonth() == 1) || (date.getMonth() == 2)) {
			return false;
		}
		
		//check if sessione estiva
		if ((date.getMonth() == 6) || (date.getMonth() == 7) || (date.getMonth() == 8)) {
			return false;
		}
		
		// IF NOTHING ELSE, IT'S A BUSINESS DAY
		return true;
	}

}
