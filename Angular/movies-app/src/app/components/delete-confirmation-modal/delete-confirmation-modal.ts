import { Component, input, output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-delete-confirmation-modal',
  imports: [ButtonModule, DialogModule],
  templateUrl: './delete-confirmation-modal.html',
  styleUrl: './delete-confirmation-modal.css',
})
export class DeleteConfirmationModal {

  /**
   * INPUT and OUTPUT
   *    - move data between components
   * 
   *    - input: parent -> child
   *        - assigning a variable some data
   * 
   *    - output: child -> parent
   *        - broadcast an event and the parent will need to listen for that event
   *            - event payload will contain the data that needed to be sent   
   * 
   *    - deprecated versions: @Input and @Output
   */

  // creating variables that need to be passed in by parent
  visible = input.required<boolean>();
  recordName = input.required<string>();

  // creating events for when the delete is confirmed or cancelled
  confirmed = output<void>();   // creates an event called "confirmed"
  cancelled = output<void>();   // creates an event called "cancelled"


  // IF YOU NEED TO SET A PAYLOAD:
  // set the datatype in output<DataType>();
  handleEvent() {
    // create your payload data
    //payload = {}

    // then emit the event 
    //this.cancelled.emit(payload);
  }
}
