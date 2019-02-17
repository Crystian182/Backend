package it.unisalento.se.saw.restapi;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.cglib.transform.impl.AddDelegateTransformer;
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

import it.unisalento.se.saw.Iservices.IToolService;
import it.unisalento.se.saw.dto.TicketMessageDTO;
import it.unisalento.se.saw.dto.ToolDTO;
import it.unisalento.se.saw.dto.UserDTO;




@RunWith(MockitoJUnitRunner.class)
public class ToolRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private IToolService toolServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new ToolRestController(toolServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<ToolDTO> tools = new ArrayList<ToolDTO>();
		
		ToolDTO tool1 = new ToolDTO();
		tool1.setId(1);
		tool1.setName("Proiettore");
		tool1.setQuantity(2);
		tools.add(tool1);
		
		when(toolServiceMock.getAll()).thenReturn(tools);
		
		mockMvc.perform(get("/tool/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("Proiettore")))
			.andExpect(jsonPath("$[0].quantity", is(2)));
		
		verify(toolServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(toolServiceMock);
	}
	
	@Test
	public void deleteTest() throws Exception {
			
		mockMvc.perform(post("/tool/delete/{id}", 1))
			.andExpect(status().isOk());
		
		verify(toolServiceMock, times(1)).delete(1);;
		verifyNoMoreInteractions(toolServiceMock);
	}
	
	public void saveTest() throws Exception {
		
		ToolDTO tool1 = new ToolDTO();
		tool1.setId(1);
		tool1.setName("Proiettore");
		tool1.setQuantity(2);
		
		when(toolServiceMock.save(tool1)).thenReturn(tool1);
		
		mockMvc.perform(post("/tool/save").contentType(MediaType.APPLICATION_JSON).content(asJsonString(tool1)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("Proiettore")))
			.andExpect(jsonPath("$.quantity", is(2)));;
		
		verify(toolServiceMock, times(1)).save(tool1);
		verifyNoMoreInteractions(toolServiceMock);
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