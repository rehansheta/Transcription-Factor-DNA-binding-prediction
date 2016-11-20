# Transcription-Factor-DNA-binding-prediction

# Introduction
Transcription factors (TFs) control the expression of genes through sequence-specific interactions
with genomic DNA. Different TFs bind preferentially to different sequences, with the majority
recognizing short (6-12 base), degenerate ‘motifs’. Because many types of genomic analyses
involve scanning for potential TF binding sites, modeling the sequence specificities of TFs is a
central problem in understanding the function and evolution of genomes.
Ideally, models of TF sequence binding specificity should predict the relative affinity (e.g.
dissociation constant) to different individual sequences, and/or the probability of occupancy at
any position in the genome. Currently, the major paradigm in modeling TF sequence specificity is
the Position Weight Matrix (PWM) model. However, it is increasingly recognized that
shortcomings of PWMs, such as their inability to model gaps, to capture dependencies between
the residues in the binding site, or to account for the fact that TFs can have more than one DNAbinding
interface, can make them inaccurate. Alternative models that address some of the
shortcomings of PWMs have been developed, but their relative efficacies have not been directly
compared.
