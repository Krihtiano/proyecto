select inscrit.* from inscrit
inner join soci on inscrit.soci_id = soci.id 
left join estadistica_modalitat on estadistica_modalitat.soci_id = soci.id
where inscrit.torneig_id = 3 and inscrit.grup_num = NULL and estadistica_modalitat.modalitat_id = 1 
order by estadistica_modalitat.coeficient_base desc

