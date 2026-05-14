#!/bin/bash
# ============================================================
# AC2 - Script de inicialização dos dois sistemas
# Uso: ./start.sh
# ============================================================

export JAVA_HOME="/home/ph/.local/share/JetBrains/Toolbox/apps/intellij-idea/jbr"
export MVN="/home/ph/.m2/wrapper/dists/apache-maven-3.9.12-bin/5nmfsn99br87k5d4ajlekdq10k/apache-maven-3.9.12/bin/mvn"
export PATH="$JAVA_HOME/bin:$PATH"

BASE="$(cd "$(dirname "$0")" && pwd)"

echo "======================================================"
echo "  AC2 - Sistemas Backend Spring Boot                  "
echo "======================================================"
echo "  Java:  $(java -version 2>&1 | head -1)"
echo "  Maven: $MVN"
echo "======================================================"

# Inicia Parte 1: Sistema de Projetos (porta 8080)
echo ""
echo "[1/2] Iniciando Sistema de Projetos (porta 8080)..."
cd "$BASE/projetos"
$MVN spring-boot:run &
PID1=$!
echo "  PID: $PID1"

# Inicia Parte 2: PetCare (porta 8081)
echo ""
echo "[2/2] Iniciando PetCare (porta 8081)..."
cd "$BASE/petcare"
$MVN spring-boot:run &
PID2=$!
echo "  PID: $PID2"

echo ""
echo "  Aguardando servidores subirem (~30 segundos)..."
sleep 35

echo ""
echo "======================================================"
echo "  ✅ Sistema de Projetos: http://localhost:8080       "
echo "  ✅ H2 Projetos:   http://localhost:8080/h2-console  "
echo "     JDBC URL: jdbc:h2:mem:projetos_db               "
echo ""
echo "  ✅ PetCare:       http://localhost:8081             "
echo "  ✅ H2 PetCare:    http://localhost:8081/h2-console  "
echo "     JDBC URL: jdbc:h2:mem:petcare_db                "
echo ""
echo "  🌐 Frontend: abra frontend/index.html no browser    "
echo "======================================================"
echo ""
echo "  Pressione CTRL+C para parar ambos"

trap "kill $PID1 $PID2 2>/dev/null; echo 'Servidores parados.'; exit 0" SIGINT SIGTERM
wait $PID1 $PID2
