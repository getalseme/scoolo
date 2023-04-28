


Vue.createApp({
    data(){
            return {
                activePage: 0,
                pages: [
                    {
                        pageTitle: 'Home page',
                        content: 'Wellcome to the wonderful world of underground music',
                        link: {text: 'Home', url: 'index.html'}
                    },
                    {
                        pageTitle: 'About page',
                        content: 'bla bla bla bla bla',
                        link: {text: 'About', url: 'about.html'}
                    },
                    {
                        pageTitle: 'Contact page',
                        content: 'GET THE FUCK OUT',
                        link: {text: 'Contact', url: 'contact.html'}
                    }
                ]
            };
        }
    }).mount('body');

