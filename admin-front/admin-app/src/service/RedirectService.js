/* eslint-disable prettier/prettier */
class RedirectService {
    static redirectToName(router, name) {
      router.push({ name: name }).catch((err) => {
        if (err.name != "NavigationDuplicated") {
          console.error(err);
        }
      });
    }

    static redirectToUrl(router, url) {
      router.push(url).catch((err) => {
        if (err.name != "NavigationDuplicated") {
          console.error(err);
        }
      });
  }
}
export default RedirectService;