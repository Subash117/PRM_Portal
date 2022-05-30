import Route from '@ember/routing/route';

export default class AnswersRoute extends Route {
  queryParams = {
    pid: { refreshModel: false },
    uid: { refreshModel: false },
  };

  async model(params) {
    console.log(params);
    let response = await fetch(
      `http://localhost:8080/PRM_portal/getanswer?uid=${params.uid}&pid=${params.pid}`,
      { credentials: 'include' }
    );
    let data = await response.json();
    console.log(data.answers);
    return data.answers;
  }
}
