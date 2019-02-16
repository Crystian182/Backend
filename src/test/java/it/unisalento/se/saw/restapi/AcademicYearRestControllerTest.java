package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.se.saw.Iservices.IAcademicYearService;
import it.unisalento.se.saw.dto.AcademicYearDTO;
import it.unisalento.se.saw.dto.TermDTO;
import it.unisalento.se.saw.repositories.AcademicYearRepository;

@RunWith(MockitoJUnitRunner.class)
public class AcademicYearRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IAcademicYearService academicYearServiceMock;
	
	@Mock
	private AcademicYearRepository AcademicYearRepository;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new AcademicYearRestController(academicYearServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<TermDTO> terms = new ArrayList<TermDTO>();
		
		TermDTO term1 = new TermDTO();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		TermDTO term2 = new TermDTO();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
		
		List<AcademicYearDTO> years = new ArrayList<AcademicYearDTO>();

		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		year1.setTerms(terms);
		years.add(year1);
		
		AcademicYearDTO year2 = new AcademicYearDTO();
		year2.setIdacademicYear(2);
		year2.setYear(2019);
		year2.setTerms(terms);
		years.add(year2);
		
		when(academicYearServiceMock.getAll()).thenReturn(years);
		
		mockMvc.perform(get("/academicyear/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idacademicYear", is(1)))
			.andExpect(jsonPath("$[0].year", is(2018)))
			.andExpect(jsonPath("$[0].terms[0].idterm", is(1)))
			.andExpect(jsonPath("$[0].terms[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[1].idterm", is(2)))
			.andExpect(jsonPath("$[0].terms[1].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[1].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].idacademicYear", is(2)))
			.andExpect(jsonPath("$[1].year", is(2019)))
			.andExpect(jsonPath("$[1].terms[0].idterm", is(1)))
			.andExpect(jsonPath("$[1].terms[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[1].idterm", is(2)))
			.andExpect(jsonPath("$[1].terms[1].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[1].end", is(mockDate.getTime())));
		
		verify(academicYearServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	@Test
	public void getTermsByAcademicYearIdTest() throws Exception {
		Date mockDate = new Date();
		
		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		List<TermDTO> terms = new ArrayList<TermDTO>();
		
		TermDTO term1 = new TermDTO();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		TermDTO term2 = new TermDTO();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
		when(academicYearServiceMock.getTermsByAcademicYearId(1)).thenReturn(terms);
		
		mockMvc.perform(get("/academicyear/getTermsByAaId/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idterm", is(1)))
			.andExpect(jsonPath("$[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].idterm", is(2)))
			.andExpect(jsonPath("$[1].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].end", is(mockDate.getTime())));
		
		verify(academicYearServiceMock, times(1)).getTermsByAcademicYearId(1);
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	@Test
	public void getAllYearsOfCourseTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<TermDTO> terms = new ArrayList<TermDTO>();
		
		TermDTO term1 = new TermDTO();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		terms.add(term1);
		
		TermDTO term2 = new TermDTO();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		terms.add(term2);
		
		
		List<AcademicYearDTO> years = new ArrayList<AcademicYearDTO>();

		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		year1.setTerms(terms);
		years.add(year1);
		
		AcademicYearDTO year2 = new AcademicYearDTO();
		year2.setIdacademicYear(2);
		year2.setYear(2019);
		year2.setTerms(terms);
		years.add(year2);
		
		when(academicYearServiceMock.getAllYearsOfCourse(1)).thenReturn(years);
		
		mockMvc.perform(get("/academicyear/getAllYearsOfCourse/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idacademicYear", is(1)))
			.andExpect(jsonPath("$[0].year", is(2018)))
			.andExpect(jsonPath("$[0].terms[0].idterm", is(1)))
			.andExpect(jsonPath("$[0].terms[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[1].idterm", is(2)))
			.andExpect(jsonPath("$[0].terms[1].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[0].terms[1].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].idacademicYear", is(2)))
			.andExpect(jsonPath("$[1].year", is(2019)))
			.andExpect(jsonPath("$[1].terms[0].idterm", is(1)))
			.andExpect(jsonPath("$[1].terms[0].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[0].end", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[1].idterm", is(2)))
			.andExpect(jsonPath("$[1].terms[1].start", is(mockDate.getTime())))
			.andExpect(jsonPath("$[1].terms[1].end", is(mockDate.getTime())));
		
		verify(academicYearServiceMock, times(1)).getAllYearsOfCourse(1);
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	public void saveTest() throws Exception {
		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		when(academicYearServiceMock.save(year1)).thenReturn(year1);
		
		mockMvc.perform(post("/academicyear/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(year1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idacademicYear", is(1)))
			.andExpect(jsonPath("$.year", is(2018)));
		
		verify(academicYearServiceMock, times(1)).save(year1);
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	public void saveTermTest() throws Exception {
		Date mockDate = new Date();
		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		TermDTO term1 = new TermDTO();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		
		when(academicYearServiceMock.saveTerm(term1)).thenReturn(term1);
		
		mockMvc.perform(post("/academicyear/saveTerm").contentType(MediaType.APPLICATION_JSON).content(asJsonString(term1)))
			.andExpect(status().isOk());
		
		verify(academicYearServiceMock, times(1)).saveTerm(term1);
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	public void saveAllTermTest() throws Exception {
		Date mockDate = new Date();
		
		AcademicYearDTO year1 = new AcademicYearDTO();
		year1.setIdacademicYear(1);
		year1.setYear(2018);
		
		List<TermDTO> terms = new ArrayList<TermDTO>();
		
		TermDTO term1 = new TermDTO();
		term1.setIdterm(1);
		term1.setStart(mockDate);
		term1.setEnd(mockDate);
		term1.setAcademicYear(year1);
		terms.add(term1);
		
		TermDTO term2 = new TermDTO();
		term2.setIdterm(2);
		term2.setStart(mockDate);
		term2.setEnd(mockDate);
		term2.setAcademicYear(year1);
		terms.add(term2);
		
		when(academicYearServiceMock.saveAllTerm(terms)).thenReturn(terms);
		
		mockMvc.perform(post("/academicyear/saveAllTerm").contentType(MediaType.APPLICATION_JSON).content(asJsonString(terms)))
			.andExpect(status().isOk());
		
		verify(academicYearServiceMock, times(1)).saveAllTerm(terms);
		verifyNoMoreInteractions(academicYearServiceMock);
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}
