package br.com.buzzo.jbehave;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.util.List;
import java.util.Locale;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.executors.SameThreadExecutors;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.selenium.PerStoriesWebDriverSteps;
import org.jbehave.web.selenium.PropertyWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;
import org.jbehave.web.selenium.WebDriverSteps;

public class ExampleStory extends JUnitStories {

//    static {
//        System.setProperty("browser", "phantomjs");
//        System.setProperty("phantomjs.binary.path", "/opt/phantomjs");
//    }

    private WebDriverProvider driverProvider = new PropertyWebDriverProvider();
    private WebDriverSteps    lifecycleSteps = new PerStoriesWebDriverSteps(this.driverProvider);
    private SeleniumContext   context        = new SeleniumContext();

    public ExampleStory() {
        if (this.lifecycleSteps instanceof PerStoriesWebDriverSteps) {
            configuredEmbedder().useExecutorService(new SameThreadExecutors().create(configuredEmbedder().embedderControls()));
        }
    }

    @Override
    public Configuration configuration() {
        final Keywords keywords = new LocalizedKeywords(new Locale("pt"));
        final Class<? extends Embeddable> embeddableClass = this.getClass();
        return new SeleniumConfiguration()
                .useSeleniumContext(this.context)
                // -------------------------------------------------------------------------
                // usando o selenium para os testes
                .useWebDriverProvider(this.driverProvider)
                // -------------------------------------------------------------------------
                // texto em portugues BR
                .useStoryParser(new RegexStoryParser(keywords))
                .useKeywords(keywords)
                // -------------------------------------------------------------------------
                // deve falhar o este quanto encontrar pending ou failure? sim!
                .useFailureStrategy(new FailingUponPendingStep())
                .usePendingStepStrategy(new FailingUponPendingStep())
                // -------------------------------------------------------------------------
                // pequena janela para acompanhar os steps
                // .useStepMonitor(new SeleniumStepMonitor(new LocalFrameContextView().sized(500, 100), this.context,
                // new SilentStepMonitor()))
                // -------------------------------------------------------------------------
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder().withCodeLocation(codeLocationFromClass(embeddableClass)).withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT, Format.HTML, Format.XML));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        final Configuration configuration = configuration();
        return new InstanceStepsFactory(configuration, new ExampleSteps(this.driverProvider), this.lifecycleSteps,
                new WebDriverScreenshotOnFailure(this.driverProvider, configuration.storyReporterBuilder()));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/example.story"), null);
    }

}
