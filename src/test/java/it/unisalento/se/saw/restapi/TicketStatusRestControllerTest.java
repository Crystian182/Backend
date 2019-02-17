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

import it.unisalento.se.saw.Iservices.ITicketStatusService;
import it.unisalento.se.saw.dto.TicketStatusDTO;



@RunWith(MockitoJUnitRunner.class)
public class TicketStatusRestControllerTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),MediaType.APPLICATION_JSON.getSubtype(),Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private ITicketStatusService ticketStatusServiceMock;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new TicketStatusRestController(ticketStatusServiceMock)).setViewResolvers(viewResolver()).build();
	}
	
	@Test
	public void getAllTest() throws Exception {
		
		Date mockDate = new Date();
		
		List<TicketStatusDTO> ticketStatuses = new ArrayList<TicketStatusDTO>();
		
		TicketStatusDTO ticketStatus1 = new TicketStatusDTO();
		ticketStatus1.setIdstatus(1);
		ticketStatus1.setDescription("In lavorazione");
		ticketStatuses.add(ticketStatus1);
		
		TicketStatusDTO ticketStatus2 = new TicketStatusDTO();
		ticketStatus2.setIdstatus(2);
		ticketStatus2.setDescription("Risolto");
		ticketStatuses.add(ticketStatus2);
		
		
		when(ticketStatusServiceMock.getAll()).thenReturn(ticketStatuses);
		
		mockMvc.perform(get("/ticketStatus/getAll"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].idstatus", is(1)))
			.andExpect(jsonPath("$[0].description", is("In lavorazione")))
			.andExpect(jsonPath("$[1].idstatus", is(2)))
			.andExpect(jsonPath("$[1].description", is("Risolto")));
		
		verify(ticketStatusServiceMock, times(1)).getAll();
		verifyNoMoreInteractions(ticketStatusServiceMock);
	}
	
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

}