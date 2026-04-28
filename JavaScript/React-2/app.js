function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
      <div className="container">
        <a className="navbar-brand fw-bold text-primary" href="#">Sorriso Saudável</a>

        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuNavbar">
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="menuNavbar">
          <ul className="navbar-nav ms-auto me-3">
            <li className="nav-item"><a className="nav-link" href="#inicio">Início</a></li>
            <li className="nav-item"><a className="nav-link" href="#servicos">Serviços</a></li>
            <li className="nav-item"><a className="nav-link" href="#sobre">Sobre</a></li>
            <li className="nav-item"><a className="nav-link" href="#contato">Contato</a></li>
          </ul>

          <a className="btn btn-primary" href="#preatendimento">Agendar consulta</a>
        </div>
      </div>
    </nav>
  );
}

function Hero() {
  return (
    <section id="inicio" className="hero-section d-flex align-items-center">
      <div className="container">
        <div className="row align-items-center">

          <div className="col-md-6">
            <h1 className="display-5 fw-bold">
              Cuide do seu sorriso com profissionais especializados
            </h1>

            <p className="lead mt-3">
              Na Clínica Sorriso Saudável oferecemos atendimento humanizado,
              tecnologia moderna e foco total no bem-estar dos nossos pacientes.
            </p>

            <a href="#preatendimento" className="btn btn-primary btn-lg mt-3">
              Solicitar atendimento
            </a>
          </div>

          <div className="col-md-6 text-center">
            <img
              src="https://images.unsplash.com/photo-1588776814546-1ffcf47267a5"
              className="img-fluid rounded"
              alt=""
            />
          </div>

        </div>
      </div>
    </section>
  );
}

function TituloSecao({ titulo, subtitulo }) {
  return (
    <div className="text-center mb-5">
      <h2>{titulo}</h2>
      {subtitulo && <p className="text-muted">{subtitulo}</p>}
    </div>
  );
}

function Section({ id, className, children }) {
  return (
    <section id={id} className={`py-5 ${className || ""}`}>
      <div className="container">
        {children}
      </div>
    </section>
  );
}

function CardServico({ icon, titulo, texto }) {
  return (
    <div className="col-md-6 col-lg-3">
      <div className="card h-100 shadow-sm">
        <div className="card-body text-center">
          <i className={`bi ${icon} fs-1 text-primary`}></i>
          <h5 className="card-title mt-3">{titulo}</h5>
          <p className="card-text">{texto}</p>
        </div>
      </div>
    </div>
  );
}

function Servicos() {
  const listaServicos = [
    {
      icon: "bi-emoji-smile",
      titulo: "Limpeza Dental",
      texto: "Remoção de placas bacterianas e prevenção de doenças bucais."
    },
    {
      icon: "bi-shield-plus",
      titulo: "Restauração Dentária",
      texto: "Recuperação da estrutura do dente com materiais modernos e duráveis."
    },
    {
      icon: "bi-braces",
      titulo: "Ortodontia",
      texto: "Correção do alinhamento dos dentes e melhora da estética do sorriso."
    },
    {
      icon: "bi-stars",
      titulo: "Clareamento Dental",
      texto: "Procedimentos seguros para deixar seu sorriso mais branco e saudável."
    }
  ];

  return (
    <Section id="servicos" className="bg-light">

      <TituloSecao
        titulo="Nossos Serviços"
        subtitulo="Tratamentos odontológicos realizados com segurança e qualidade."
      />

      <div className="row g-4">
        {listaServicos.map((item, index) => (
          <CardServico key={index} {...item} />
        ))}
      </div>

    </Section>
  );
}

function Sobre() {
  return (
    <Section id="sobre">
      <div className="row align-items-center">

        <div className="col-md-6">
          <img
            src="https://images.unsplash.com/photo-1588776814546-1ffcf47267a5"
            className="img-fluid rounded"
            alt=""
          />
        </div>

        <div className="col-md-6">
          <h2>Sobre a Clínica</h2>
          <p>Texto institucional...</p>
        </div>

      </div>
    </Section>
  );
}

function Contato() {
  return (
    <Section id="contato" className="bg-light">
      <TituloSecao titulo="Contato" />

      <div className="row text-center">
        <div className="col-md-4">
          <i className="bi bi-telephone fs-2 text-primary"></i>
          <p className="mt-2">(62) 99999-9999</p>
        </div>
        <div className="col-md-4">
          <i className="bi bi-geo-alt fs-2 text-primary"></i>
          <p className="mt-2">Av. Exemplo, 123</p>
        </div>
        <div className="col-md-4">
          <i className="bi bi-clock fs-2 text-primary"></i>
          <p className="mt-2">08h às 18h</p>
        </div>
      </div>
    </Section>
  );
}

function Preatendimento() {
  return (
    <Section id="preatendimento">
      <TituloSecao titulo="Pré-Atendimento" />

      <div className="row justify-content-center">
        <div className="col-lg-6">
          <form>
            <input className="form-control mb-3" placeholder="Nome" />
            <input className="form-control mb-3" placeholder="Contato" />
            <textarea className="form-control mb-3"></textarea>
            <button className="btn btn-primary w-100">Enviar</button>
          </form>
        </div>
      </div>
    </Section>
  );
}

function Footer() {
  return (
    <footer className="bg-dark text-white text-center py-3">
      <p>© 2026 Clínica Sorriso Saudável</p>
    </footer>
  );
}

function App() {
  return (
    <React.Fragment>
      <Navbar />
      <Hero />
      <Servicos />
      <Sobre />
      <Contato />
      <Preatendimento />
      <Footer />
    </React.Fragment>
  );
}

const root = ReactDOM.createRoot(document.getElementById("app"));
root.render(<App />);