SELECT i.*
FROM inscrit i, soci s, modalitat m, estadistica_modalitat em
LEFT JOIN soci ON soci.id = inscrit.soci_id
LEFT JOIN estadistica_modalitat on s.id = em.soci_id
LEFT JOIN modalitat on em.modalitat_id = m.id
LEFT JOIN torneig on i.torneig_id = torneig.id
where m.id = 1
ORDER BY em.coeficient_base;