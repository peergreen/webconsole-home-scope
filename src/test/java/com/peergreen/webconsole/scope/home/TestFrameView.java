/**
 * Peergreen S.A.S. All rights reserved.
 * Proprietary and confidential.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peergreen.webconsole.scope.home;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammed Boukada
 */
public class TestFrameView {

    FrameView frameView = new FrameView();
    Component component1 = new Button();
    Component component2 = new Label();
    Component component3 = new CssLayout();

    @Test
    public void testAddFrame() {
        frameView.addFrame(component1, "component1");

        Assert.assertTrue(!frameView.getNextButton().isVisible(), "Next button must be hidden if there is one or no components in the frame");
        Assert.assertTrue(!frameView.getPreviousButton().isVisible(), "Previous button must be hidden if there is one or no components in the frame");

        frameView.addFrame(component2, "component2");

        Assert.assertTrue(frameView.getNextButton().isVisible(), "Next button must be visible if there is more than one components in the frame");
        Assert.assertTrue(frameView.getPreviousButton().isVisible(), "Previous button must be visible if there is more than one components in the frame");

        frameView.addFrame(component3, "component3");
    }

    @Test
    public void testNextButtonNavigation() {
        Assert.assertEquals(frameView.getFrameState(), 0, "Initial frame state must be 0");
        frameView.getNextButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component2, "Next component must be component2");
        Assert.assertEquals(frameView.getFrameState(), 1, "Next frame state must be 1");
        frameView.getNextButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component3, "Next component must be component3");
        Assert.assertEquals(frameView.getFrameState(), 2, "Next frame state must be 2");
        frameView.getNextButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component1, "Next component must be component1");
        Assert.assertEquals(frameView.getFrameState(), 0, "Next frame state must be 0");
    }

    @Test
    public void testPreviousButtonNavigation() {
        frameView.getPreviousButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component3, "Previous component must be component3");
        Assert.assertEquals(frameView.getFrameState(), 2, "Previous frame state must be 2");
        frameView.getPreviousButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component2, "Previous component must be component2");
        Assert.assertEquals(frameView.getFrameState(), 1, "Previous frame state must be 1");
        frameView.getPreviousButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component1, "Previous component must be component1");
        Assert.assertEquals(frameView.getFrameState(), 0, "Previous frame state must be 0");
    }

    @Test
    public void testRemoveFrame() {
        // frame state = 0
        // Components content : component1 -> component2 -> component3
        frameView.removeFrame(component1);
        // Components content : component2 -> component3
        Assert.assertEquals(frameView.getComponents().get(0), component2, "");

        frameView.addFrame(component1, "component1");
        // Components content : component2 -> component3 -> component1
        frameView.removeFrame(component2);
        // Components content : component3 -> component1
        frameView.getNextButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component1, "Next component must be component1");
        frameView.getPreviousButton().click();
        Assert.assertEquals(frameView.getComponents().get(frameView.getFrameState()), component3, "Previous component must be component3");

        frameView.removeFrame(component3);
        // Components content : component1
        Assert.assertTrue(!frameView.getNextButton().isVisible(), "Next button must be hidden because there is only component1");
        Assert.assertTrue(!frameView.getPreviousButton().isVisible(), "Previous button must be hidden because there is only component1");
    }
}
