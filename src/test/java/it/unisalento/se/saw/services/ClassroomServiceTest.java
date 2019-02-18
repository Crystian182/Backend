package it.unisalento.se.saw.services;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.dialect.function.TemplateRenderer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.unisalento.se.saw.domain.Building;
import it.unisalento.se.saw.domain.Classroom;
import it.unisalento.se.saw.domain.ClassroomHasTool;
import it.unisalento.se.saw.domain.ClassroomHasToolId;
import it.unisalento.se.saw.domain.Tool;
import it.unisalento.se.saw.dto.BuildingDTO;
import it.unisalento.se.saw.dto.ClassroomDTO;
import it.unisalento.se.saw.dto.DayDTO;
import it.unisalento.se.saw.dto.SchedulerDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.TypeLessonDTO;
import it.unisalento.se.saw.exceptions.BuildingNotFoundException;
import it.unisalento.se.saw.exceptions.ClassroomNotFoundException;
import it.unisalento.se.saw.repositories.ClassroomHasToolRepository;
import it.unisalento.se.saw.repositories.ClassroomRepository;

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
public class ClassroomServiceTest {
    
    @Mock
    ClassroomRepository classroomRepository;
    
    @Mock
    ClassroomHasToolRepository classroomHasToolRepository;

    @InjectMocks
    ClassroomService classroomService;

    @Test()
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
    	List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findAll()).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(classrooms.get(0).getIdclassroom())).thenReturn(classroomHasTools);

        List<ClassroomDTO> classroomDTOs = classroomService.getAll();
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getAll2Test() throws IOException {
    	
    	Date mockDate = new Date();
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
    	List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findAll()).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(classrooms.get(0).getIdclassroom())).thenReturn(null);

        List<ClassroomDTO> classroomDTOs = classroomService.getAll();
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdTest() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
        when(classroomRepository.findById(1)).thenReturn(Optional.of(classroom1));
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(classroomHasTools);

        ClassroomDTO classroomDTO = classroomService.getById(1);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTO.getId());
    }
    
    @Test()
    public void getById2Test() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
        when(classroomRepository.findById(1)).thenReturn(Optional.of(classroom1));
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(null);

        ClassroomDTO classroomDTO = classroomService.getById(1);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTO.getId());
    }
    
    @Test()
    public void getByIdBuildingTest() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClassesByBuild(1)).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(classroomHasTools);

        List<ClassroomDTO> classroomDTOs = classroomService.getByIdBuilding(1);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdBuilding2Test() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClassesByBuild(1)).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(null);

        List<ClassroomDTO> classroomDTOs = classroomService.getByIdBuilding(1);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdBuildingAndNameTest() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClasses("Stecca", 1)).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(classroomHasTools);

        List<ClassroomDTO> classroomDTOs = classroomService.getByIdBuildingAndName(1, "Stecca");
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByIdBuildingAndName2Test() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClasses("Stecca", 1)).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(null);

        List<ClassroomDTO> classroomDTOs = classroomService.getByIdBuildingAndName(1, "Stecca");
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByNameTest() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClassesByName("I1")).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(classroomHasTools);

        List<ClassroomDTO> classroomDTOs = classroomService.getByName("I1");
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByName2Test() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
        when(classroomRepository.findClassesByName("I1")).thenReturn(classrooms);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(null);

        List<ClassroomDTO> classroomDTOs = classroomService.getByName("I1");
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test()
    public void getByAvailableIdBuildingTest() throws IOException, ClassroomNotFoundException {
    	
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
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
    	
		List<Classroom> classrooms = new ArrayList<Classroom>();
		Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		classrooms.add(classroom1);
		
		TermDTO term = new TermDTO();
		term.setIdterm(1);
		term.setStart(mockDate);
		term.setEnd(mockDate);
		
		SchedulerDTO scheduler = new SchedulerDTO();
		scheduler.setTerm(term);
		
		DayDTO day = new DayDTO();
		day.setIdDay(1);
		
		TypeLessonDTO typeLesson = new TypeLessonDTO();
		typeLesson.setScheduler(scheduler);
		typeLesson.setStart(mockDate);
		typeLesson.setDay(day);
		typeLesson.setEnd(mockDate);
		
        when(classroomRepository.findAvailableClassesByBuild(1, 1, mockDate, mockDate, 1)).thenReturn(classrooms);

        List<ClassroomDTO> classroomDTOs = classroomService.getAvailableByIdBuilding(1, typeLesson);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTOs.get(0).getId());
    }
    
    @Test
    public void saveTest() throws BuildingNotFoundException {
		
		ClassroomHasToolId id = new ClassroomHasToolId();
		id.setIdclassroom(1);
		id.setIdtool(1);
		
		Tool tool = new Tool();
    	tool.setIdtool(1);
    	tool.setName("Proiettore");
    	
    	List<ClassroomHasTool> classroomHasTools = new ArrayList<ClassroomHasTool>();
    	ClassroomHasTool classroomHasTool = new ClassroomHasTool();
    	classroomHasTool.setId(id);
    	classroomHasTool.setQuantity(2);
    	classroomHasTool.setTool(tool);
    	classroomHasTools.add(classroomHasTool);
    	
    	Building building1 = new Building();
		building1.setIdbuilding(1);
		building1.setName("Stecca");
		building1.setAddress("Via Ecotekne");
		building1.setLat((float) 17.9);
		building1.setLng((float) 21.3);
		
    	Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
		classroom1.setBuilding(building1);
		
		List<ToolDTO> toolDTOs = new ArrayList<ToolDTO>();
		ToolDTO tool1 = new ToolDTO();
		tool1.setId(1);
		tool1.setName("Proiettore");
		tool1.setQuantity(2);
		toolDTOs.add(tool1);
		
		BuildingDTO buildingDTO1 = new BuildingDTO();
		buildingDTO1.setId(1);
		buildingDTO1.setName("Stecca");
		buildingDTO1.setAddress("Via Ecotekne");
		buildingDTO1.setLat((float) 17.9);
		buildingDTO1.setLng((float) 21.3);
		
		ClassroomDTO classroomDTO1 = new ClassroomDTO();
		//classroomDTO1.setId(1);
		classroomDTO1.setName("I1");
		classroomDTO1.setSeats(180);
		classroomDTO1.setLat((float) 17.9);
		classroomDTO1.setLng((float) 21.3);
		classroomDTO1.setBuilding(buildingDTO1);
		classroomDTO1.setTool(toolDTOs);
		
		when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom1);
        when(classroomHasToolRepository.getToolByClassroomId(1)).thenReturn(classroomHasTools);

        ClassroomDTO classroomDTO = classroomService.save(classroomDTO1);
        assertEquals(classroom1.getIdclassroom(),(Integer) classroomDTO.getId());
    	
    }
    
    @Test
    public void deleteTest() throws ClassroomNotFoundException {
    	
    	Classroom classroom1 = new Classroom();
		classroom1.setIdclassroom(1);
		classroom1.setName("I1");
		classroom1.setSeats(180);
		classroom1.setLat((float) 17.9);
		classroom1.setLng((float) 21.3);
    	
    	when(classroomRepository.findById(1)).thenReturn(Optional.of(classroom1));
    	
    	classroomService.delete(1);
    }
    
}