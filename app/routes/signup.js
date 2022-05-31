import Route from '@ember/routing/route';
import { service } from '@ember/service';

export default class SignupRoute extends Route {
  @service router;
  async model(params) {
    let response = await fetch(
      `http://localhost:8080/PRM_portal/checksignup?pid=${params.pid}`
    );
    let data = await response.json();
    if (!data.allow) {
      this.router.transitionTo('notfound', 404);
    }
    return params.pid;
  }
}
