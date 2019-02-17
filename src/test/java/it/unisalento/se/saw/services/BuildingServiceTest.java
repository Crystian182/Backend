package it.unisalento.se.saw.services;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.unisalento.se.saw.domain.AcademicYear;
import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.Term;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.repositories.AcademicYearRepository;
import it.unisalento.se.saw.repositories.BuildingRepository;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.ClassroomRepository;
import it.unisalento.se.saw.repositories.TermRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.imageio.IIOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BuildingServiceTest {

    @Mock
    BuildingRepository buildingRepository;
    
    @Mock
    ClassroomRepository classroomRepository;
    
    @Mock
    ClassroomHasToolRepository classroomHasToolRepository;

    @InjectMocks
    BuildingService buildingService;

    @Test(expected=javax.imageio.IIOException.class)
    public void getAllTest() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	List<ClassroomHasTool> classroomHasTools = new ArrayList<ClassroomHasTool>();
    	
    	ClassroomHasToolId id = new ClassroomHasToolId();
    	id.setIdclassroom(1);
    	id.setIdtool(1);
    	
    	Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
    	
    	ClassroomHasTool classroomHasTool = new ClassroomHasTool();
    	classroomHasTool.setId(id);
    	classroomHasTool.setQuantity(2);
    	classroomHasTool.setTool(tool);
    	
    	List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classrooms.add(classroom1);
		
		List<Building> buildings = new ArrayList<Building>();
		Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		buildings.add(building1);
    	
    	List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		Classroom classroomDTO1 = new Classroom();
		classroomDTO1.setIdclassroom(1);
		classroomDTO1.setName("I1");
		classroomDTO1.setSeats(180);
		classroomDTO1.setLat((float) 17.9);
		classroomDTO1.setLng((float) 21.3);
		classrooms.add(classroomDTO1);

        when(buildingRepository.findAll()).thenReturn(buildings);
        when(classroomRepository.findClassesByBuild(buildings.get(0).getIdbuilding())).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(classrooms.get(0).getIdclassroom())).thenReturn(classroomHasTools);
        when(buildingService.getImage(1)).thenReturn("abc");

        List<BuildingDTO> buildingDTOs = buildingService.getAll();
        assertEquals(building1.getIdbuilding(),(Integer) buildingDTOs.get(0).getId());
    }
    
    @Test
    public void getByIdTest() throws IOException, BuildingNotFoundException {
    	
    	Date mockDate = new Date();
    	
    	List<ClassroomHasTool> classroomHasTools = new ArrayList<ClassroomHasTool>();
    	
    	ClassroomHasToolId id = new ClassroomHasToolId();
    	id.setIdclassroom(1);
    	id.setIdtool(1);
    	
    	Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
    	
    	ClassroomHasTool classroomHasTool = new ClassroomHasTool();
    	classroomHasTool.setId(id);
    	classroomHasTool.setQuantity(2);
    	classroomHasTool.setTool(tool);
    	
    	List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classrooms.add(classroom1);
		
		List<Building> buildings = new ArrayList<Building>();
		Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		buildings.add(building1);
    	
    	List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		Classroom classroomDTO1 = new Classroom();
		classroomDTO1.setIdclassroom(1);
		classroomDTO1.setName("I1");
		classroomDTO1.setSeats(180);
		classroomDTO1.setLat((float) 17.9);
		classroomDTO1.setLng((float) 21.3);
		classrooms.add(classroomDTO1);

        when(buildingRepository.findById(1)).thenReturn(Optional.of(building1));
        when(classroomRepository.findClassesByBuild(buildings.get(0).getIdbuilding())).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(classrooms.get(0).getIdclassroom())).thenReturn(classroomHasTools);

        BuildingDTO buildingDTO = buildingService.getById(1);
        assertEquals(building1.getIdbuilding(),(Integer) buildingDTO.getId());
    }

    public void saveTest() throws BuildingNotFoundException {
    	
    	Set<ClassroomHasTool> chastools = new HashSet<ClassroomHasTool>();
    	
    	Tool tool = new Tool();
		tool.setIdtool(1);
		tool.setName("Proiettore");
		
		ClassroomHasToolId id = new ClassroomHasToolId();
		id.setIdclassroom(1);
		id.setIdtool(1);
		
		ClassroomHasTool chastool = new ClassroomHasTool();
		chastool.setId(id);
		chastool.setTool(tool);
		chastool.setQuantity(2);
		chastools.add(chastool);
		
    	Set<Classroom> classrooms = new HashSet<Classroom>();
    	Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setClassroomHasTools(chastools);
		classrooms.add(classroom1);
		
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		building1.setClassrooms(classrooms);
		
		List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
		ToolDTO tool1 = new ToolDTO();
		tool1.setId(1);
		tool1.setName("Proiettore");
		tool1.setQuantity(2);
		toolDTOs.add(tool1);
		
		List<ClassroomDTO> classroomDTOs = new ArrayList<ClassroomDTO>();
		ClassroomDTO classroomDTO1 = new ClassroomDTO();
		classroomDTO1.setId(1);
		classroomDTO1.setName("I1");
		classroomDTO1.setSeats(180);
		classroomDTO1.setLat((float) 17.9);
		classroomDTO1.setLng((float) 21.3);
		classroomDTO1.setTool(toolDTOs);
		classroomDTOs.add(classroomDTO1);
		
		BuildingDTO buildingDTO1 = new BuildingDTO();
		buildingDTO1.setId(1);
		buildingDTO1.setName("Stecca");
		buildingDTO1.setAddress("Via Ecotekne");
		buildingDTO1.setLat((float) 17.9);
		buildingDTO1.setLng((float) 21.3);
		buildingDTO1.setClassrooms(classroomDTOs);

        when(buildingRepository.save(any(Building.class))).thenReturn(building1);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom1);

        BuildingDTO b = buildingService.save(buildingDTO1);
        assertEquals(buildingDTO1.getId(), b.getId());
    	
    }
    
    public void deleteTest() throws BuildingNotFoundException {
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
    	when(buildingRepository.findById(1)).thenReturn(Optional.of(building1));
    	
    	buildingService.delete(1);
    }
   
    /*@Test
    public void saveTest() {
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);

        when(academicYearRepository.save(any(AcademicYear.class))).thenReturn(year1);

        AcademicYearDTO y = academicYearService.save(yearDTO1);
        assertEquals(yearDTO1.getIdacademicYear(), y.getIdacademicYear());
    	
    }
    
    @Test
    public void saveTermTest() {
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
    	
    	Date mockDate = new Date();
    	Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);
		
		TermDTO termDTO1 = new TermDTO();
		termDTO1.setIdterm(1);
		termDTO1.setStart(mockDate);
		termDTO1.setEnd(mockDate);
		termDTO1.setAcademicYear(yearDTO1);
		
        when(termRepository.save(any(Term.class))).thenReturn(term1);

        TermDTO termDTO = academicYearService.saveTerm(termDTO1);
        assertEquals(termDTO1.getIdterm(), termDTO.getIdterm());
    	
    }
    
    @Test
    public void saveAllTermTest() {
    	
    	AcademicYear year1 = new AcademicYear();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
    	
    	List<Term> terms = new ArrayList<Term>();
    	Date mockDate = new Date();
    	Term term1 = new Term();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		term1.setNumber(1);
		terms.add(term1);
		
		AcademicYearDTO yearDTO1 = new AcademicYearDTO();
		yearDTO1.setIdacademicYear(1);
		yearDTO1.setYear(2018);
		
		List<TermDTO> termDTOs = new ArrayList<TermDTO>();
		TermDTO termDTO1 = new TermDTO();
		termDTO1.setIdterm(1);
		termDTO1.setStart(mockDate);
		termDTO1.setEnd(mockDate);
		termDTO1.setAcademicYear(yearDTO1);
		termDTOs.add(termDTO1);
		
        when(termRepository.saveAll(Matchers.anyListOf(Term.class))).thenReturn(terms);

        List<TermDTO> termDTOsres = academicYearService.saveAllTerm(termDTOs);
        assertEquals(termDTO1.getIdterm(), termDTOsres.get(0).getIdterm());
    	
    }*/
    
}