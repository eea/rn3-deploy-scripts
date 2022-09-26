package org.eea.validation.kafka.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import java.util.HashMap;
import java.util.Map;
import org.eea.exception.EEAException;
import org.eea.kafka.domain.EEAEventVO;
import org.eea.kafka.domain.EventType;
import org.eea.validation.util.ValidationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * The Class ExecuteDatasetValidationCommandTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExecuteDatasetValidationCommandTest {

  /**
   * The execute dataset validation command.
   */
  @InjectMocks
  private ExecuteDatasetValidationCommand executeDatasetValidationCommand;

  /**
   * The kie base.
   */
  @Mock
  private KieBase kieBase;
  /**
   * the validation helper
   */
  @Mock
  private ValidationHelper validationHelper;

  /**
   * The data.
   */
  private Map<String, Object> data;

  /**
   * The eea event VO.
   */
  private EEAEventVO eeaEventVO;


  /**
   * Inits the mocks.
   */
  @Before
  public void initMocks() {
    data = new HashMap<>();
    data.put("uuid", "uuid");
    data.put("dataset_id", "1");
    data.put("kieBase", kieBase);
    data.put("numPag", 1);
    data.put("task_id", 1);
    eeaEventVO = new EEAEventVO();
    eeaEventVO.setEventType(EventType.COMMAND_VALIDATE_DATASET);
    eeaEventVO.setData(data);
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Gets the event type test.
   *
   * @return the event type test
   */
  @Test
  public void getEventTypeTest() {
    assertEquals(EventType.COMMAND_VALIDATE_DATASET,
        executeDatasetValidationCommand.getEventType());
  }

  @Test
  public void getNotificationEventType() {
    assertEquals(EventType.COMMAND_VALIDATED_DATASET_COMPLETED,
        executeDatasetValidationCommand.getNotificationEventType());
  }

  /**
   * Execute test.
   *
   * @throws EEAException the EEA exception
   */
  @Test
  public void executeTest() throws EEAException {

    executeDatasetValidationCommand.execute(eeaEventVO);

    Mockito.verify(validationHelper, times(1)).processValidation(Mockito.anyLong(),
        Mockito.eq(eeaEventVO), Mockito.eq("uuid"), Mockito.eq(1l), Mockito.any(),
        Mockito.eq(EventType.COMMAND_VALIDATED_DATASET_COMPLETED));
  }


}
